package com.example.snivvy.present.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.snivvy.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.background
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.snivvy.present.navigation.Routes
import kotlinx.coroutines.delay


@Composable

fun SplashScreen(navHostController: NavHostController){
    LaunchedEffect(Unit) {
        delay(2000)
        navHostController.navigate(Routes.WelcomeScreen.route){
        popUpTo(Routes.SplashScreen.route){inclusive=true}
        }
    }
    Box(modifier= Modifier.fillMaxSize().background(Color(0xFFF5F0E6))){
        Image(painter= painterResource(R.drawable.snivvy), contentDescription = null,modifier = Modifier.size(270.dp).align(Alignment.Center))
        Column(modifier=Modifier.align(Alignment.BottomCenter).padding(bottom=25.dp),horizontalAlignment=Alignment.CenterHorizontally){
            Text(text="Created by",fontSize=16.sp,fontWeight=FontWeight.Bold)
            Text(text= stringResource(R.string.your_name),fontSize=18.sp,fontWeight= FontWeight.Bold,color=Color(0XFF8B6D50))

        }
    }

}

