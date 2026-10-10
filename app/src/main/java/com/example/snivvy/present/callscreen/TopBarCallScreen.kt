package com.example.snivvy.present.callscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snivvy.R

@Composable
fun TopBarCalls(){
    //declaring variables
    var isSearching by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)) {
                if (isSearching) {
                    TextField(
                        value = search, onValueChange = {
                            search = it
                        },
                        placeholder = { Text(text = "Search") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Transparent,
                            focusedContainerColor = Transparent,
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent
                        ),
                        modifier = Modifier.padding(start = 12.dp),
                        singleLine = true
                    )
                } else {
                    Text(
                        text = "Calls",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 12.dp, top = 25.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                if (isSearching) {//this code will user click on search button
                    IconButton(onClick = {
                        isSearching = false
                        search = ""
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.cross_icon),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else {//this will execute when user not click on search button

                    IconButton(onClick = { isSearching = true }) {
                        Icon(
                            painter = painterResource(R.drawable.search_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(45.dp)
                                .padding(top = 20.dp)
                        )
                    }
                    IconButton(onClick = {
                        showMenu = true
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.threedot_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(45.dp)
                                .padding(top = 20.dp)
                        )

                        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false },
                            modifier = Modifier
                                .height(60.dp)
                                .width(120.dp)  //  Controls menu width
                                .padding(end = 8.dp)) {

                            DropdownMenuItem(
                                text = { Text(text = "Settings") },
                                onClick = { showMenu = false })
                        }
                    }
                }
            }
            HorizontalDivider()
        }
    }
}