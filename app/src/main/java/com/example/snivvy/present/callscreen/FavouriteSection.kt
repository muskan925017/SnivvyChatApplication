package com.example.snivvy.present.callscreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snivvy.R

@Composable
@Preview(showSystemUi = true)
fun FavouriteSection() {
    val sampleFavourite=listOf(
        FavouriteContact(R.drawable.profile_icon,"Howris Lewis"),
        FavouriteContact(R.drawable.profile_icon,"Andrew Smith"),
        FavouriteContact(R.drawable.profile_icon,"Robert Harris"),
        FavouriteContact(R.drawable.profile_icon,"Robin Thomas"),
        FavouriteContact(R.drawable.profile_icon,"Adam White")

    )
    Column(modifier = Modifier.padding(start = 16.dp, top=18.dp,bottom = 8.dp)) {
        Text(
            text = "Favourites",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 7.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())//we are making row scrollable
        ) {
           sampleFavourite.forEach{
               FavouritesItem(it)
           }
        }
    }
}
data class FavouriteContact(val image:Int,val name:String)