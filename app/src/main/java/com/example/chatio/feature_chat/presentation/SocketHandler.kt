package com.example.chatio.feature_chat.presentation

import com.example.chatio.feature_chat.presentation.util.Message
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SocketHandler  {
    val localMsg : MutableStateFlow<Message> = MutableStateFlow(Message("","",""))
    var socket: Socket? = null


    suspend fun registerOnNewChat() {



    }

//    fun initAndGetSocket(): Socket? {
//
//        return socket
//    }

    fun disconnectSocket() {
        socket?.disconnect()
        socket?.off()
    }

    fun emitChat(msg: Message) {
        val jsonStr = Gson().toJson(msg, Message::class.java)
        socket?.emit(CHAT_KEYS.NEW_MESSAGE, jsonStr)
    }

    object CHAT_KEYS {
        const val NEW_MESSAGE = "new_message"
    }

    companion object {
        const val SOCKET_URL = "http://10.0.2.2:3000"
    }

}

interface OnMsgReceived{
    fun onRecieve(msg: Message)
}