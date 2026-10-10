package com.example.snivvy.present.bottom_navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.snivvy.R

@Composable
fun BottomNavigation(
    navHostController: NavHostController,
    onClick: (index: Int) -> Unit,
    selectedItem : Int
) {
    //BottomAppBar is used for bottom navigation bar

    val items = listOf(
        NavigationItem("Chats", R.drawable.chat_icon, R.drawable.outline_chat_image),
        NavigationItem("Updates", R.drawable.update_icon, R.drawable.update_icon),
        NavigationItem("Communities", R.drawable.society_img_icon, R.drawable.society_img_icon),
        NavigationItem("Calls", R.drawable.call_icon, R.drawable.phone_icon)
    )
    NavigationBar(
        containerColor = colorResource(R.color.brown_snivvy2),
        modifier = Modifier.padding(vertical = 6.dp)  // safe spacing
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onClick(index) },
                label = {
                    if (index == selectedItem) {
                        Text(item.name, color = Color.Black)
                    } else {
                        Text(item.name, color = Color.DarkGray)

                    }
                },
                icon = {
                    Icon(
                        painter = if (
                            index == selectedItem
                        ) {
                          painterResource(item.selectedIcon)
                        }else{
                            painterResource(item.unselectedIcon)
                        },contentDescription = null,
                        tint = if(index == selectedItem){
                            Color.DarkGray
                        }else{
                            Color.Black
                        }, modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = colorResource(R.color.brown_snivvy)
                )
            )
        }
    }

}

data class NavigationItem(
    val name: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
)