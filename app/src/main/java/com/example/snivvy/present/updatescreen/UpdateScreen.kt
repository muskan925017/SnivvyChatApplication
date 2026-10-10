package com.example.snivvy.present.updatescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
fun UpdateScreen(navHostController: NavHostController) {
    val scrollState = rememberScrollState()
//    val sampleStatus = listOf(
//        StatusData(image = R.drawable.profile_icon, "Howris Lewis", time = "10 minutes ago"),
//        StatusData(image = R.drawable.profile_icon, "Taylor Wilson", time = "53 minutes ago"),
//        StatusData(image = R.drawable.profile_icon, "Adam White", time = "Today 10:00 AM"),
//        StatusData(image = R.drawable.profile_icon, "Andrew Smith", time = "Today 10:00 AM")
//
//
//    )
    val sampleChannel = listOf(
        Channels(image=R.drawable.neatroots,name="Neat Roots", description = "Latest news in tech"),
        Channels(image=R.drawable.foodlove,name="Food Lover",description="Discover new recipes"),
        Channels(image=R.drawable.meta,name="Meta",description="Explore the World")
    )


    Scaffold(
        modifier = Modifier, containerColor = colorResource(R.color.background_snivvy),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}, containerColor = colorResource(R.color.brown_snivvy2),
                modifier = Modifier
                    .padding(bottom = 360.dp)
                    .size(65.dp)
            )
            {//container area of floating action button where we add camera icon
                Icon(
                    painter = painterResource(R.drawable.camera_image_updates),
                    contentDescription = null,
                    tint = Color(0xFFF5F0E6),
                    modifier = Modifier.size(35.dp)
                )

            }
        },
        //bottomBar is used inside the scaffold for bottom navigation bar
        bottomBar = {
            BottomNavigation(navHostController,selectedItem = 0,onClick={index ->
                when(index){
                    0 -> {navHostController.navigate(Routes.HomeScreen.route)}
                    1 -> {navHostController.navigate(Routes.UpdateScreen.route)}
                    2 -> {navHostController.navigate(Routes.CommunitiesScreen.route)}
                    3 -> {navHostController.navigate(Routes.CallScreen.route)}


                }
            })

        },
        topBar = {
            TopBar()
        }
    ) {
        //whenever we use scaffold we have to initialize by default padding
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Status",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
            MyStatus()
//            sampleStatus.forEach {
//                StatusItem(statusData = it)
//            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.LightGray, thickness = 2.dp)


            //Channel creation
            Text(
                text = "Channels",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
            Spacer(modifier=Modifier.height(8.dp))
            Column(modifier=Modifier.padding(horizontal=16.dp)){
                Text(
                    text="Stay updated on your favorite topics — explore channels to follow below.",
                    color=Color.Black,
                    fontSize=16.sp
                    )
                Spacer(modifier=Modifier.height(32.dp))
                Text(
                    text="Explore channels to follow"
                )
            }
            Spacer(modifier=Modifier.height(16.dp))
            sampleChannel.forEach{
                ChannelItemDesign(channel=it)
            }


        }
    }
}

