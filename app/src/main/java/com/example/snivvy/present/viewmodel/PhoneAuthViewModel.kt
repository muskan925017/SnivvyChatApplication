package com.example.snivvy.present.viewmodel

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.snivvy.models.PhoneAuthUser
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
import androidx.core.content.edit
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseDatabase
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Ideal)
    val authState = _authState.asStateFlow()
    private val userRef = database.reference.child("users")

    fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity
    ) {//this function send otp on user's mobile
        _authState.value = AuthState.Loading
        val option = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            //this is firebase callback that will do three functions that are onCodeSent,onVerificationCompleted and onVerificationFailed
            override fun onCodeSent(
                id: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {//to send the code on user's mobile
                super.onCodeSent(id, token)
                Log.d("PhoneAuth", "onCodeSent triggered. Verification ID:$id")
                _authState.value = AuthState.CodeSent(verificationId = id)
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {//sometimes firebase verifies the otp automatically
                signWithCredential(credential, context = activity)
            }

            override fun onVerificationFailed(exception: FirebaseException) {//when verification failed
                Log.e("PhoneAuth", "Verification failed:${exception.message}")
                _authState.value = AuthState.Error(exception.message ?: "Verification Failed")
            }

        }
        val phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)//user has limited time to enter the otp
            .setActivity(activity)//reference of current activity means showing ui for otp process
            .setCallbacks(option)//to verify otp and handles the error
            .build()
        //here we are telling the firebase that send the otp and then firebase process the phone number and sends the otp
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)


    }

    private fun signWithCredential(
        credential: PhoneAuthCredential,
        context: Context
    ) {//if user is valid credential then it will execute if block otherwise else block
        _authState.value = AuthState.Loading //waiting or loading indicator
        firebaseAuth.signInWithCredential(credential)//check user is valid or not by using user's phone number
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val phoneAuthUser = PhoneAuthUser(
                        userId = user?.uid
                            ?: "", //if user and userid is null then it will store empty string
                        phoneNumber = user?.phoneNumber ?: ""
                    )
                    markUserAsSignedIn(context)
                    _authState.value = AuthState.Success(phoneAuthUser)

                    fetchUserProfile(user?.uid ?: "")
                } else {
                    //if sign-in is failed then it will display sign-in failed message to user
                    _authState.value = AuthState.Error(task.exception?.message ?: "Sign-In failed")
                }

            }

    }

    private fun markUserAsSignedIn(context: Context) {//context creates app's environment state and used to access the app's data
        //sharedPreference is a variable that is  the storage in the app and data is stored in the value pair
        val sharedPreference = context.getSharedPreferences(
            "app_prefs",
            Context.MODE_PRIVATE
        )//app-prefs is a file that will stores app data and Context.MODE_PRIVATE means app data will not access by other app
        sharedPreference.edit().putBoolean("isSignedIn",true).apply()
//here we are editing the sharedPreference variable using edit()
        //isSignedIn means user is signed in or not and apply() will save data instantly
    }

    fun fetchUserProfile(userId: String) {//userId provides unique id with the help of that we can fetch the user profile
        val userRef =
            userRef.child(userId)//userRef is used to fetch users detail that stored in firebase

        //here we are fetching users data using get() if we successfully fetch the data then addOnSuccessListener will execute otherwise addOnFailureListener will execute
        userRef.get().addOnSuccessListener { snapshot ->//snapshot holds the user's data
            if (snapshot.exists()) {//here we are checking that user's data is exist or not
                val userProfile =
                    snapshot.getValue(PhoneAuthUser::class.java)//here we are converting user's data into PhoneAuthUser type
                if (userProfile != null) {
                    _authState.value = AuthState.Success(userProfile)
                }
            }

        }
            .addOnFailureListener {//if we faces any error then it will execute and display given message
                _authState.value = AuthState.Error("Failed to fetch user profile")
            }

    }

    //this function will verify the code
    fun verifyCode(otp: String, context: Context) {
        val currentAuthState = _authState.value
        if (currentAuthState !is AuthState.CodeSent || currentAuthState.verificationId.isEmpty()) {
            Log.e(
                "PhoneAuth",
                "Attempting to verify OTP without a valid verification ID"
            )//checks the id
            _authState.value = AuthState.Error("Verification not started or Invalid ID")
            return
        }//if otp is correct then this code will execute
        val credential = PhoneAuthProvider.getCredential(currentAuthState.verificationId, otp)
        signWithCredential(credential, context)//verifies the otp in the backend
    }

    fun saveUserProfile(userId: String, name: String, status: String, profileImage: Bitmap?) {
        val database = FirebaseDatabase.getInstance().reference
        val encodedImage = profileImage?.let { convertBitmapToBase64(it) }
        val userProfile = PhoneAuthUser(
            userId = userId,
            name = name,
            status = status,
            phoneNumber = Firebase.auth.currentUser?.phoneNumber ?: "",
            profileImage = encodedImage
        )
        database.child("users").child(userId).setValue(userProfile)
    }

    //here we are converting image into string and then in bitmap and after that we will convert image into string from bitmap to string so we don't need to buy firebase subscription
    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()//here image will store in the form of bytes
        //here image is compress in JPEG format and quality will be good like 100% quality of image
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        //Base64 is used to send the data in text form example with the help of API we can do this
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    //after log out of user auth state must be reset so here we are creating function for this
    fun resetAuthState(){
           _authState.value=AuthState.Ideal
    }
    //user is logging out from firebase
    fun signOut(activity:Activity){
        firebaseAuth.signOut()
        val sharedPreference=activity.getSharedPreferences("app_prefs",Activity.MODE_PRIVATE)
        sharedPreference.edit().putBoolean("isSigned",false).apply()
    }
}

sealed class AuthState() {
    object Ideal : AuthState()
    object Loading : AuthState()
    data class CodeSent(val verificationId: String) : AuthState()
    data class Success(val user: PhoneAuthUser) : AuthState()
    data class Error(val message: String) : AuthState()
}