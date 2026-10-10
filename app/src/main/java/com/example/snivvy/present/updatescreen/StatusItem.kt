package com.example.snivvy.present.updatescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snivvy.R

@Composable
@Preview(showSystemUi = true)

fun MyStatus() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.profile_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )
            Icon(
                painter = painterResource(R.drawable.plus_icon),
                contentDescription = null,
                tint = Color(0xFFF5F0E6),
                modifier = Modifier
                    .size(31.dp)
                    .align(Alignment.BottomEnd)
                    .padding(2.dp)
                    .background(
                        color = colorResource(R.color.brown_snivvy2),
                        shape = CircleShape
                    )
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = "My Status",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "Tap to add status update",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

    }
}
//to generate multiple status
//data class StatusData(val image:Int,val name:String,val time:String)
//    @Composable
//    fun StatusItem(statusData: StatusData){
//        Row(modifier=Modifier.fillMaxWidth().padding(horizontal=10.dp,vertical=1.dp),
//            verticalAlignment=Alignment.CenterVertically)
//        {
//            Image(painter=painterResource(statusData.image),
//                contentDescription=null,
//                modifier=Modifier.size(70.dp)
//                    .padding(4.dp)
//                    .clip(shape=CircleShape),
//                     contentScale=ContentScale.Crop
//            )
//            Spacer(modifier=Modifier.width(8.dp))
//            Column{
//                Text(text=statusData.name,
//                    fontSize=16.sp,
//                    fontWeight=FontWeight.Bold
//                )
//                Text(text=statusData.time,
//                    fontSize=14.sp,
//                    fontWeight=FontWeight.Bold,
//                    color=Color.Gray
//                )
//            }
//        }
//    }

