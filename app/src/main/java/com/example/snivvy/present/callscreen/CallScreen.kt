package com.example.snivvy.present.callscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.snivvy.R
import com.example.snivvy.present.bottom_navigation.BottomNavigation
import com.example.snivvy.present.navigation.Routes

@Composable
fun CallScreen(navHostController: NavHostController) {
//    val sampleCalls = listOf(
//        Call(R.drawable.profile_icon,"Robert Harris","Today, 4:00 PM",false),
//        Call(R.drawable.profile_icon,"Andrew Smith","Today, 4:45 PM",true),
//        Call(R.drawable.profile_icon,"Robin Thomas","Today, 6:19 PM",false),
//        Call(R.drawable.profile_icon,"Adam White","Yesterday, 9:38 AM",true),
//        Call(R.drawable.profile_icon,"Taylor Wilson","Yesterday, 7:10 PM",false),
//        Call(R.drawable.profile_icon,"Howris Lewis","Yesterday, 8:43 PM",false),
//        Call(R.drawable.profile_icon,"Andrew Smith","Yesterday, 10:47 PM",true)
//
//
//
//
//
//
//
//    )
    Scaffold(
        modifier = Modifier, containerColor = colorResource(R.color.background_snivvy),
        topBar = {
            TopBarCalls()
        },
        bottomBar = {
            BottomNavigation(navHostController,selectedItem = 0,onClick={index ->
                when(index){
                    0 -> {navHostController.navigate(Routes.HomeScreen.route)}
                    1 -> {navHostController.navigate(Routes.UpdateScreen.route)}
                    2 -> {navHostController.navigate(Routes.CommunitiesScreen.route)}
                    3 -> {navHostController.navigate(Routes.CallScreen.route)}


                }
            })
        },floatingActionButton = {
            FloatingActionButton(
                onClick = {}, containerColor = colorResource(R.color.brown_snivvy2),
                modifier = Modifier
                    .padding(bottom = 360.dp)
                    .size(65.dp)
            )
            {//container area of floating action button where we add camera icon
                Icon(
                    painter = painterResource(R.drawable.calladd_icon),
                    contentDescription = null,
                    tint = Color(0xFFF5F0E6),
                    modifier = Modifier.size(65.dp)
                )

            }
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            FavouriteSection()
            Button(
                onClick = {}, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.brown_snivvy2),
                )
            ) {
                Text(text = "Start a new call",
                    fontSize=16.sp,
                    fontWeight= FontWeight.Bold,
                    color=colorResource(R.color.background_snivvy))

            }
            Spacer(modifier=Modifier.height(8.dp))
            Text(text="Recent Calls",
                fontSize=20.sp,
                fontWeight=FontWeight.Bold,
                modifier=Modifier.padding(horizontal=16.dp,vertical=8.dp)
            )
            LazyColumn{
//                items(sampleCalls){data-> CallItemDesign(data)

                }
            }


        }
    }

