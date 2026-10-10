package com.example.snivvy.present.communitiesscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.snivvy.R
import com.example.snivvy.present.bottom_navigation.BottomNavigation
import com.example.snivvy.present.navigation.Routes
import com.example.snivvy.present.updatescreen.TopBar

@Composable
fun CommunitiesScreen(navHostController: NavHostController) {
    val sampleCommunities=listOf(
        Communities(R.drawable.techenthusiasts,"Tech Enthusiasts","256 members"),
        Communities(R.drawable.photography,"Photophile","154 members"),
        Communities(R.drawable.travellerunited,"Traveller United","150 members")
    )
    Scaffold(
        modifier = Modifier, containerColor = colorResource(R.color.background_snivvy),
        topBar = {
            TopBarCommunity()

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
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)

        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.brown_snivvy2)
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Text(
                    text = "Start a new Community",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color=colorResource(R.color.background_snivvy)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your Communities",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyColumn {
                        items(sampleCommunities){
                            CommunityItemDesign(it)
                        }
            }
        }
    }
}