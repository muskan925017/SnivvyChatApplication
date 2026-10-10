package com.example.snivvy.present.viewmodel

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.snivvy.models.PhoneAuthUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import java.io.ByteArrayOutputStream

@HiltViewModel
class NewPhoneAuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseDatabase
) : ViewModel() {

    private val _authState = MutableStateFlow<NewAuthState>(NewAuthState.Ideal)
    val authState = _authState.asStateFlow()

    private val userRef = database.reference.child("users")

    private val fakeOtp = "123456"  // 🔥 FIXED OTP FOR TESTING

    // -------------------------------------------------------------------------
    // 1️⃣ SEND FAKE OTP (NO FIREBASE)
    // -------------------------------------------------------------------------
    fun sendVerificationCode(phoneNumber: String, activity: Activity) {
        Log.d("FakeOTP", "Sending Fake OTP to $phoneNumber. OTP = $fakeOtp")

        _authState.value = NewAuthState.CodeSent("FAKE_VERIFICATION_ID")
    }


    // -------------------------------------------------------------------------
    // 2️⃣ VERIFY FAKE OTP
    // -------------------------------------------------------------------------
    fun verifyCode(otp: String, context: Context) {

        val currentState = _authState.value

        if (currentState !is NewAuthState.CodeSent) {
            _authState.value = NewAuthState.Error("OTP not sent yet")
            return
        }

        if (otp == fakeOtp) {
            fakeLoginSuccess(context)
        } else {
            _authState.value = NewAuthState.Error("Invalid OTP")
        }
    }


    // -------------------------------------------------------------------------
    // 3️⃣ LOGIN SUCCESS WITHOUT REAL OTP / FIREBASE CREDENTIALS
    // -------------------------------------------------------------------------
    private fun fakeLoginSuccess(context: Context) {

        val fakeUserId = System.currentTimeMillis().toString()

        val newUser = PhoneAuthUser(
            userId = fakeUserId,
            phoneNumber = "FakeNumber",
            name = "",
            status = "",
            profileImage = null
        )

        markUserAsSignedIn(context)

        // Save user to Firebase DB
        userRef.child(fakeUserId).setValue(newUser)

        _authState.value = NewAuthState.Success(newUser)
    }


    // -------------------------------------------------------------------------
    // SAVE LOGIN STATUS
    // -------------------------------------------------------------------------
    private fun markUserAsSignedIn(context: Context) {
        val sharedPreference = context.getSharedPreferences(
            "app_prefs",
            Context.MODE_PRIVATE
        )
        sharedPreference.edit().putBoolean("isSignedIn", true).apply()
    }

    // -------------------------------------------------------------------------
    // FETCH USER PROFILE
    // -------------------------------------------------------------------------
    fun fetchUserProfile(userId: String) {
        val ref = userRef.child(userId)

        ref.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val userProfile = snapshot.getValue(PhoneAuthUser::class.java)
                if (userProfile != null) {
                    _authState.value = NewAuthState.Success(userProfile)
                }
            }
        }.addOnFailureListener {
            _authState.value = NewAuthState.Error("Failed to fetch user profile")
        }
    }


    // -------------------------------------------------------------------------
    // SAVE PROFILE WITH IMAGE
    // -------------------------------------------------------------------------
    fun saveUserProfile(userId: String, name: String, status: String, profileImage: Bitmap?) {
        val database = FirebaseDatabase.getInstance().reference
        val encodedImage = profileImage?.let { convertBitmapToBase64(it) }

        val userProfile = PhoneAuthUser(
            userId = userId,
            name = name,
            status = status,
            phoneNumber = "FakeNumber",
            profileImage = encodedImage
        )

        database.child("users").child(userId).setValue(userProfile)
    }


    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }


    // -------------------------------------------------------------------------
    // RESET STATE
    // -------------------------------------------------------------------------
    fun resetAuthState() {
        _authState.value = NewAuthState.Ideal
    }

    // -------------------------------------------------------------------------
    // LOGOUT
    // -------------------------------------------------------------------------
    fun signOut(activity: Activity) {
        firebaseAuth.signOut()
        val sharedPreference = activity.getSharedPreferences("app_prefs", Activity.MODE_PRIVATE)
        sharedPreference.edit().putBoolean("isSigned", false).apply()
    }
}


// -----------------------------------------------------------------------------
// AUTH STATES
// -----------------------------------------------------------------------------
sealed class NewAuthState {
    object Ideal : NewAuthState()
    object Loading : NewAuthState()
    data class CodeSent(val verificationId: String) : NewAuthState()
    data class Success(val user: PhoneAuthUser) : NewAuthState()
    data class Error(val message: String) : NewAuthState()
}
