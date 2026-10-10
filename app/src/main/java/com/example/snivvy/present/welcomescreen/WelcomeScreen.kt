package com.example.snivvy.present.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.snivvy.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.snivvy.present.navigation.Routes


@Composable
fun WelcomeScreen(navHostController: NavHostController) {
    Box(modifier=Modifier.fillMaxSize().background(Color(0xFFF5F0E6)).padding(top=100.dp,start=30.dp,end=30.dp),contentAlignment = Alignment.TopCenter) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Image(
                painter = painterResource(R.drawable.welcomeimage_second),
                contentDescription = null,
                modifier = Modifier.size(450.dp)
            )
            Text(
                text="Welcome to Snivvy",fontSize=35.sp,color=Color(0XFF8B6D50),fontWeight= FontWeight.Bold
            )
            Row{
             Text(
                 text="Because every message matters",fontSize=16.sp,fontWeight=FontWeight.Bold,fontStyle= FontStyle.Italic
             )
            }
            Spacer(modifier=Modifier.height(27.dp))
            Button(onClick = {navHostController.navigate(Routes.UserRegistrationScreen.route)},
                modifier=Modifier.size(280.dp,50.dp),
                shape= RoundedCornerShape(8.dp),
                colors= ButtonDefaults.buttonColors(
                    colorResource(R.color.brown_snivvy)
                )
            ){
                   Text(
                       text="Next",
                       fontSize=30.sp,
                       fontWeight = FontWeight.Bold,
                       color= Color(0xFFF5F0E6)
                   )
            }
        }
    }
}