
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.platform.LocalDensity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.snivvy.R
import com.example.snivvy.present.navigation.Routes
import com.example.snivvy.present.viewmodel.AuthState
import com.example.snivvy.present.viewmodel.PhoneAuthViewModel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.draw.drawBehind
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults


@SuppressLint("ContextCastToActivity")
@Composable
fun AuthScreen(
    navController: NavHostController,
    phoneAuthViewModel: PhoneAuthViewModel = hiltViewModel()
) {
    val authState by phoneAuthViewModel.authState.collectAsState()

    val context = LocalContext.current
    val activity = context as Activity

    // UI state variables
    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("India") }
    var countryCode by remember { mutableStateOf("+91") }
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var verificationId by remember { mutableStateOf<String?>(null) }
    var anchorSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0E6))
    ) {
        // ----------- MAIN UI (VERTICAL FORM) -----------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



//            Spacer(modifier = Modifier.height(40.dp))

            // Image
            Image(
                painter = painterResource(R.drawable.phonimage),
                contentDescription = null,
                modifier = Modifier.size(170.dp)
            )

            // Title
            Text(
                text = "What's Your Phone Number",
                fontSize = 23.sp,
                color = colorResource(R.color.brown_snivvy),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(40.dp))

            // COUNTRY DROPDOWN BUTTON
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

            // ----------- PHONE INPUT OR OTP INPUT -----------
            when (authState) {

                is AuthState.Ideal,
                is AuthState.Loading,
                is AuthState.CodeSent -> {

                    // Save verification id when received
                    if (authState is AuthState.CodeSent) {
                        verificationId = (authState as AuthState.CodeSent).verificationId
                    }

                    // If verificationId is null → Show PHONE INPUT
                    if (verificationId == null) {

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
                                    phoneNumber = newValue.filter { it.isDigit()}},
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
                                if (phoneNumber.isNotEmpty()) {
                                    val fullPhone = "$countryCode$phoneNumber"
                                    phoneAuthViewModel.sendVerificationCode(fullPhone, activity)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please Enter Valid Phone Number",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(
                                colorResource(R.color.brown_snivvy2)
                            )
                        ) {
                            Text("Send OTP")
                        }

                        if (authState is AuthState.Loading) {
                            Spacer(modifier = Modifier.height(16.dp))
                            CircularProgressIndicator()
                        }

                    } else {
                        // ----------- OTP UI -----------
                        Text(
                            "Enter Otp",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.brown_snivvy2)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = otp,
                            onValueChange = { newValue ->
                                otp = newValue.filter { it.isDigit()} },
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
                                if (otp.isNotEmpty() && verificationId != null) {
                                    phoneAuthViewModel.verifyCode(otp, context)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please Enter Valid OTP",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(
                                colorResource(R.color.brown_snivvy2)
                            )
                        ) {
                            Text("Verify OTP")
                        }

                        if (authState is AuthState.Loading) {
                            Spacer(modifier = Modifier.height(16.dp))
                            CircularProgressIndicator()
                        }
                    }
                }

                is AuthState.Success -> {
                    phoneAuthViewModel.resetAuthState()
                    navController.navigate(Routes.UserProfileSetScreen.route) {
                        popUpTo(Routes.UserRegistrationScreen.route) {
                            inclusive = true
                        }
                    }
                }

                is AuthState.Error -> {
                    Toast.makeText(
                        context,
                        (authState as AuthState.Error).message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // ----------- COUNTRY DROPDOWN MENU (Must be OUTSIDE COLUMN) -----------
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(230.dp),
            offset = with(density) {
                val buttonWidth = anchorSize.width.toDp()
                val menuWidth = 230.dp
                val offsetX = (buttonWidth / 2) - (menuWidth / 2)
                DpOffset(x = offsetX, y = 0.dp)
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

