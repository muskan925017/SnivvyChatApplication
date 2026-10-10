
package com.example.snivvy.present.homescreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.snivvy.R
import com.example.snivvy.present.bottom_navigation.BottomNavigation
import com.example.snivvy.present.chat_box.ChatListModel
import com.example.snivvy.present.navigation.Routes
import com.example.snivvy.present.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController, homeBaseViewModel: BaseViewModel) {
    var showPopup by remember { mutableStateOf(false) }

    val chatData by homeBaseViewModel.chatList.collectAsState()

    val userId = FirebaseAuth.getInstance().currentUser?.uid
    LaunchedEffect(userId) {
        userId?.let {
            homeBaseViewModel.getChatForUser(it) {}
        }
    }

    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = colorResource(R.color.background_snivvy),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showPopup = true },
                containerColor = colorResource(R.color.brown_snivvy2),
                contentColor = colorResource(R.color.background_snivvy),
                modifier = Modifier.size(65.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_icon),
                    contentDescription = "Add",
                    modifier = Modifier.size(28.dp),
                    tint = colorResource(R.color.background_snivvy)
                )
            }
        },
        bottomBar = {
            BottomNavigation(navHostController, selectedItem = 0, onClick = { index ->
                when (index) {
                    0 -> navHostController.navigate(Routes.HomeScreen.route)
                    1 -> navHostController.navigate(Routes.UpdateScreen.route)
                    2 -> navHostController.navigate(Routes.CommunitiesScreen.route)
                    3 -> navHostController.navigate(Routes.CallScreen.route)
                }
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(color = colorResource(R.color.background_snivvy))
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                var isSearching by remember { mutableStateOf(false) }
                var searchText by remember { mutableStateOf("") }

                if (isSearching) {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text("Search", color = Color(0xFF747579)) },
                        singleLine = true,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 12.dp)
                            .fillMaxWidth(0.7f),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        )
                    )
                } else {
                    Text(
                        "Snivvy",
                        fontSize = 28.sp,
                        color = colorResource(R.color.brown_snivvy2),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 12.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(R.drawable.camera_image_icon),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        if (isSearching) {
                            IconButton(onClick = {
                                isSearching = false
                                searchText = ""
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.cross_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else {
                            IconButton(onClick = { isSearching = true }) {
                                Icon(
                                    painter = painterResource(R.drawable.search_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(
                                painter = painterResource(R.drawable.threedot_icon),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(color = Color.Black)
                        ) {
                            DropdownMenuItem(text = { Text("New Group") }, onClick = { showMenu = false })
                            DropdownMenuItem(text = { Text("New Broadcast") }, onClick = { showMenu = false })
                            DropdownMenuItem(text = { Text("Linked device") }, onClick = { showMenu = false })
                            DropdownMenuItem(text = { Text("Starred Messages") }, onClick = { showMenu = false })
                            DropdownMenuItem(text = { Text("Settings") }, onClick = {
                                showMenu = false
                                navHostController.navigate(Routes.SettingScreen.route)
                            })
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (showPopup) {
                AddUserPopup(
                    onDismiss = { showPopup = false },
                    onUserAdd = { newUser ->
                        try {
                            homeBaseViewModel.addChat(newUser)
                        } catch (e: Exception) {
                            Log.e("HomeScreen", "addChat failed: ${e.message}")
                        }
                        showPopup = false
                    },
                    baseViewModel = homeBaseViewModel
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn {
                items(chatData) { chat ->
                    ChatDesign(
                        chatListModel = chat,
                        onClick = {
                            navHostController.navigate(
                                Routes.ChatScreen.createRoute(chat.phoneNumber ?: "ok")
                            )
                        },
                        baseViewModel = homeBaseViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun AddUserPopup(
    onDismiss: () -> Unit,
    onUserAdd: (ChatListModel) -> Unit,
    baseViewModel: BaseViewModel
) {
    var phoneNumber by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var userFound by remember { mutableStateOf<ChatListModel?>(null) }
    var errorText by remember { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Add User by Phone", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone number") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Button(
                    onClick = {
                        if (phoneNumber.isBlank()) {
                            errorText = "Enter phone number"
                            return@Button
                        }

                        isSearching = true
                        userFound = null
                        errorText = null

                        baseViewModel.searchUserByPhoneNumber(phoneNumber.trim()) { found ->
                            isSearching = false
                            if (found != null) {
                                userFound = found
                            } else {
                                userFound = ChatListModel(
                                    name = "Unknown",
                                    phoneNumber = phoneNumber.trim(),
                                    image = null,
                                    userId = null,
                                    time = null,
                                    message = null,
                                    profileImage = null
                                )
                                errorText = "User not found — will add local contact"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.brown_snivvy2),
                        contentColor = colorResource(R.color.background_snivvy)
                    ),
                    modifier = Modifier.padding(start = 8.dp)   // ✅ FIXED
                ) {
                    Text("Search")
                }
            }
        }
    }
}