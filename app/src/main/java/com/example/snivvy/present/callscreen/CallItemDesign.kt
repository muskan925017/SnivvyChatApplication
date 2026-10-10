package com.example.snivvy.present.callscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snivvy.R

@Composable
fun CallItemDesign(call:Call) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(call.image), contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = call.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Row {
                Icon(
                    painter = painterResource(R.drawable.baseline_call_arrow),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = if (call.isMissed) {
                        Color.Red
                    } else {
                        Color(0xFF1FBB74)
                    }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text=call.time ,
                    color=Color.Gray,
                    fontSize=16.sp)
            }

        }
        Box(modifier=Modifier.fillMaxWidth()){
            IconButton(onClick={},modifier=Modifier.align(Alignment.CenterEnd)){
                Icon(painter=painterResource(R.drawable.call_icon),
                    contentDescription = null,
                    tint=Color.DarkGray,
                    modifier=Modifier.size(24.dp))
            }
        }
    }
}