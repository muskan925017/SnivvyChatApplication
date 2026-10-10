package com.example.snivvy.present.bottom_navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.snivvy.R

@Composable
@Preview(showSystemUi = true)
fun NewBottomNavigation(){
    //BottomAppBar is used for bottom navigation bar
    BottomAppBar(tonalElevation = 12.dp,
        containerColor = Color(0xFF6C4D2E)
    ){
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly){
            Column(modifier=Modifier, horizontalAlignment = Alignment.CenterHorizontally){
                Icon(painter=painterResource(R.drawable.chat_icon)
                    , contentDescription=null,
                    modifier= Modifier.size(30.dp),
                    tint=Color(0xFFF5F0E6))
                Spacer(modifier=Modifier.height(2.dp))

                Text(text="Chats",fontWeight= FontWeight.ExtraBold)
            }
            Column(modifier=Modifier, horizontalAlignment = Alignment.CenterHorizontally){
                Icon(painter=painterResource(R.drawable.update_icon),
                    contentDescription=null,
                    modifier=Modifier.size(30.dp),
                    tint=Color(0xFFF5F0E6))
                Spacer(modifier=Modifier.height(2.dp))
                Text(text="Updates",fontWeight=FontWeight.ExtraBold)
            }
            FloatingActionButton(onClick = {},
                modifier=Modifier.size(width=60.dp,height=60.dp),
                containerColor=colorResource(R.color.brown_snivvy2)
            )
            {
                Icon(painter=painterResource(R.drawable.add_icon),
                    contentDescription = "Chat Icon",
                    modifier=Modifier.size(40.dp),
                    tint=Color(0xFFFFEFD5)

                )
            }

            Column(modifier=Modifier, horizontalAlignment = Alignment.CenterHorizontally){
                Icon(painter=painterResource(R.drawable.society_img_icon),
                    contentDescription=null,
                    modifier=Modifier.size(30.dp),
                    tint=Color(0xFFF5F0E6))
                Spacer(modifier=Modifier.height(1.dp))
                Text(text="Societies",fontWeight=FontWeight.ExtraBold)

            }

            Column(modifier=Modifier, horizontalAlignment = Alignment.CenterHorizontally){
                Icon(painter=painterResource(R.drawable.phone_icon),
                    contentDescription=null,
                    modifier=Modifier.size(30.dp),
                    tint=Color(0xFFF5F0E6))
                Spacer(modifier=Modifier.height(1.dp))
                Text(text="Calls",fontWeight=FontWeight.ExtraBold)

            }
        }
    }
}