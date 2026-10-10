package com.example.snivvy.present.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.snivvy.models.Message
import com.example.snivvy.present.chat_box.ChatListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEvent
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi



class BaseViewModel : ViewModel() {
    //function for searching user via phone number
    fun searchUserByPhoneNumber(
        phoneNumber: String,
        callback: (ChatListModel?) -> Unit
    ) {//Unit means Return nothing
        val CurrentUser = FirebaseAuth.getInstance().currentUser
        if (CurrentUser == null) {
            Log.e("BaseViewModel", "User is not authenticated")
            callback(null)
            return
        }
        val databaseReference =
            FirebaseDatabase.getInstance().getReference("users")//users that is in firebase database
        databaseReference.orderByChild("phoneNumber").equalTo(phoneNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                //searching phone number
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.children.first().getValue(ChatListModel::class.java)
                        callback(user)
                    } else {
                        callback(null)
                    }
                }

                //while searching cancel that search
                override fun onCancelled(error: DatabaseError) {
                    Log.e(
                        "BaseViewModel",
                        "Error fetching User:${error.message},Details : ${error.details}"
                    )
                    callback(null)
                }
            })
    }

    //function for get chats of user
    fun getChatForUser(
        userId: String,
        callback: (List<ChatListModel>) -> Unit
    ) {//callback is the higher order function
        val chatref = FirebaseDatabase.getInstance().getReference("users/$userId/chats")
        chatref.orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                //fetching chat data
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatList = mutableListOf<ChatListModel>()
                    for (childSnapshot in snapshot.children) {
                        val chat = childSnapshot.getValue(ChatListModel::class.java)
                        if (chat != null) {
                            chatList.add(chat)

                        }
                    }
                    callback(chatList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel", "Error fetching user chats : ${error.message}")
                    callback(emptyList())
                }
            }
            )

    }

    //MutableStateFlow<> holds a value and notifies observers when the value change and emptyList is the initial value
    private val _chatList = MutableStateFlow<List<ChatListModel>>(emptyList())
    val chatList = _chatList.asStateFlow()

    //The init {} block is an initializer block that runs immediately when an object of the class is created.
    init {
        loadChatData()
    }

    //function for load chat list data
    private fun loadChatData() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserId != null) {
            val chatRef = FirebaseDatabase.getInstance().getReference("chats")

            chatRef.orderByChild("userId").equalTo(currentUserId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val chatList = mutableListOf<ChatListModel>()
                        for (childSnapshot in snapshot.children) {
                            val chat = childSnapshot.getValue(ChatListModel::class.java)
                            if (chat != null) {
                                chatList.add(chat)
                            }
                        }
                        _chatList.value = chatList
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("BaseViewModel", "Error fetching user chats : ${error.message}")

                    }
                })
        }
    }

    //function add chat
    fun addChat(newChat: ChatListModel) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserId != null) {
            val newChatRef = FirebaseDatabase.getInstance().getReference("chats").push()
            val chatWithUser = newChat.copy(currentUserId)
            newChatRef.setValue(chatWithUser).addOnSuccessListener {
                //if chat add successfully then given message will be displayed
                Log.d("BaseViewModel", "Chat added successfully to firebase")
            }.addOnFailureListener { exception ->
                Log.d("BaseViewModel", "Failed to add chat: ${exception.message}")
            }
        } else {
            Log.e("BaseViewModel", "No user is authenticated")
        }
    }

    private val dataBaseReference = FirebaseDatabase.getInstance().reference

    fun sendMessage(senderPhoneNumber: String, receiverPhoneNumber: String, messageText: String) {
        val messageId = dataBaseReference.push().key ?: return
        val message = Message(
            senderPhoneNumber = senderPhoneNumber,
            message = messageText,
            timestamp = System.currentTimeMillis()
        )
        //here we are creating node with tha name of message and its sub node is senderPhoneNumber and senderPhoneNumber subnode is receiverPhoneNumber and so on
        dataBaseReference.child("message")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)
            .child(messageId)
            .setValue(message)

        dataBaseReference.child("message")
            .child(receiverPhoneNumber)
            .child(senderPhoneNumber)
            .child(messageId)
            .setValue(message)
    }

    //Get Message function
    fun getMessage(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onNewMessage: (Message) -> Unit
    ) {
        val messageRef = dataBaseReference.child("message")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)

        messageRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)

                if (message != null) {
                    onNewMessage(message)
                }
            }

            override fun onChildChanged(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
            }

            override fun onCancelled(snapshot: DatabaseError) {
            }
        })
    }
    //Fetching last message of chats
    fun fetchLastMessageForChat(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onLastMessageFetched: (String,String)->Unit
    ){
       val chatRef = FirebaseDatabase.getInstance().reference
           .child("message")
           .child(senderPhoneNumber)
           .child(receiverPhoneNumber)

        chatRef.orderByChild("timestamp").limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val lastMessage = snapshot.children.firstOrNull()?.child("message")?.value as?String
                        val timestamp = snapshot.children.firstOrNull()?.child("timestamp")?.value as?String
                        onLastMessageFetched(lastMessage?: "No message",timestamp?: "--:--")

                    }else{
                        onLastMessageFetched("No message","--:--")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onLastMessageFetched("No message","--:--")
                }

            })
    }
    //load chat list
    fun loadChatList(
        currentUserPhoneNumber:String,
        onChatListLoaded:(List<ChatListModel>) -> Unit
    ){
        //chatList holds all the chat list
         val chatList = mutableListOf<ChatListModel>()
         val chatRef = FirebaseDatabase.getInstance().reference
             .child("chats")
             .child(currentUserPhoneNumber)

        chatRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    snapshot.children.forEach{child ->
                        val phoneNumber = child.key?:return@forEach
                        val name = child.child("name").value as?String?:"Unknown"
                        val image = child.child("image").value as?String
                        val profileImageBitmap = image?.let{decodeBase64toBitmap(it)}

                        fetchLastMessageForChat(currentUserPhoneNumber,phoneNumber){lastMessage,time ->
                            chatList.add(
                                ChatListModel(
                                    name = name,
                                    image = profileImageBitmap.toString(),
                                    message = lastMessage,
                                    time = time
                                )
                            )
                            if(chatList.size == snapshot.childrenCount.toInt()){
                                onChatListLoaded(chatList)
                            }

                        }
                    }
                }else{
                    onChatListLoaded(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onChatListLoaded(emptyList())
            }

        })
    }
    @OptIn(ExperimentalEncodingApi::class)
    private fun decodeBase64toBitmap(base64Image:String): Bitmap?{
        //if (base64Image.isNullOrBlank()) return null

        return try{
            val decodedByte = Base64.decode(base64Image,android.util.Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedByte,0,decodedByte.size)
        }catch(e: IOException){
            Log.e("DecodeImage", "Decode error: ${e.message}")
            null
        }

    }
    @OptIn(ExperimentalEncodingApi::class)
    fun base64ToBitmap(base64String:String):Bitmap?{
        return try{
            val decodedByte = Base64.decode(base64String,android.util.Base64.DEFAULT)
            val inputStream: InputStream= ByteArrayInputStream(decodedByte)
            BitmapFactory.decodeStream(inputStream)
        }catch(e :IOException){
            Log.e("DecodeImage", "Decode error: ${e.message}")
            null
        }

    }

}
