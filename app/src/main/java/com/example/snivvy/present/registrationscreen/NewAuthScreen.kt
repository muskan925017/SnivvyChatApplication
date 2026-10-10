package com.example.snivvy.present.registrationscreen

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.snivvy.R
import com.example.snivvy.present.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("ContextCastToActivity")
@Composable
fun NewAuthScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val activity = context as Activity
    val coroutineScope = rememberCoroutineScope()

    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("India") }
    var countryCode by remember { mutableStateOf("+91") }
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }

    var generatedOtp by remember { mutableStateOf<String?>(null) }

    // OTP visible UI state
    var showOtpOnScreen by remember { mutableStateOf(false) }
    var otpVisibleText by remember { mutableStateOf("") }

    var anchorSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0E6))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.phonimage),
                contentDescription = null,
                modifier = Modifier.size(170.dp)
            )

            Text(
                text = "What's Your Phone Number",
                fontSize = 23.sp,
                color = colorResource(R.color.brown_snivvy),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            TextButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .width(230.dp)
                    .onGloballyPositioned { anchorSize = it.size }
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = selectedCountry,
                        modifier = Modifier.align(Alignment.CenterStart),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterEnd),
                        tint = Color.Black
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.width(230.dp),
                thickness = 2.dp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))



            // --------- PHONE ENTER SCREEN ---------
            if (generatedOtp == null) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                ) {
                    TextField(
                        value = countryCode,
                        onValueChange = { countryCode = it },
                        modifier = Modifier.width(70.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black,
                            focusedIndicatorColor = Color.Black,
                            cursorColor = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TextField(
                        value = phoneNumber,
                        onValueChange = { newValue ->
                            phoneNumber = newValue.filter { it.isDigit() }
                        },
                        placeholder = { Text("Phone Number") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black,
                            focusedIndicatorColor = Color.Black,
                            cursorColor = Color.Black
                        )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (phoneNumber.length >= 10) {

                            val fakeOtp = (100000..999999).random().toString()
                            generatedOtp = fakeOtp

                            otpVisibleText = fakeOtp
                            showOtpOnScreen = true

                            // Hide OTP after 8 seconds (NO ERROR)
                            coroutineScope.launch {
                                delay(100000)
                                showOtpOnScreen = false
                            }

                            Toast.makeText(context, "OTP Sent: $fakeOtp", Toast.LENGTH_LONG).show()

                        } else {
                            Toast.makeText(context, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.brown_snivvy2)
                    )
                ) {
                    Text("Send OTP")
                }
            }


            // ---------------- OTP ENTER SCREEN ----------------
            else {

                if (showOtpOnScreen) {
                    Text(
                        text = "Your OTP: $otpVisibleText",
                        fontSize = 22.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Enter OTP",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.brown_snivvy2)
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = otp,
                    onValueChange = { newValue ->
                        otp = newValue.filter { it.isDigit() }
                    },
                    placeholder = { Text("OTP") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (otp == generatedOtp) {
                            Toast.makeText(context, "OTP Verified!", Toast.LENGTH_SHORT).show()

                            FakeUserData.fakeLogin(phoneNumber)
                            val session = SessionManager(context)
                            session.saveLogin(phoneNumber)

                            // store fake user
                            FakeUserData.fakeLogin(phoneNumber)

                            navController.navigate(Routes.HomeScreen.route) {
                                popUpTo(Routes.NewAuthScreen.route) { inclusive = true }
                            }

                        } else {
                            Toast.makeText(context, "Invalid OTP!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.brown_snivvy2)
                    )
                ) {
                    Text("Verify OTP")
                }
            }
        }


        // -------- COUNTRY SELECT DROPDOWN --------
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(230.dp),
            offset = with(density) {
                val bw = anchorSize.width.toDp()
                val mw = 230.dp
                DpOffset(x = (bw / 2) - (mw / 2), y = 0.dp)
            }
        ) {
            listOf("India", "USA", "Japan", "Australia", "China", "Canada")
                .sorted()
                .forEach { country ->
                    DropdownMenuItem(
                        text = { Text(country) },
                        onClick = {
                            selectedCountry = country
                            expanded = false
                        }
                    )
                }
        }
    }
}


// --------------------------------------------------
// FAKE USER LOGIN STORE
// --------------------------------------------------
object FakeUserData {
    fun fakeLogin(phone: String) {
        FakeSession.currentUser = FakeUser(
            id = phone,
            name = "User $phone",
            profilePic = ""
        )
    }
}

data class FakeUser(
    val id: String,
    val name: String,
    val profilePic: String
)

object FakeSession {
    var currentUser: FakeUser? = null
}
