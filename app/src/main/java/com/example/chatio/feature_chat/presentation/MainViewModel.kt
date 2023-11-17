package com.example.chatio.feature_chat.presentation

import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatio.feature_chat.presentation.util.Message
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val socketHandler: SocketHandler
): ViewModel() {
    var username = "SOCKET-IO"
    private val _listOfMsgs : MutableStateFlow<List<Message>> =
        MutableStateFlow(
            listOf(Message(username = username, timeStamp = "", msg = "What's Your Name"))
        )
    val listOfMsgs = _listOfMsgs.asStateFlow()

    val socket = socketHandler

    private val _inputMessage = MutableStateFlow("")
    val inputMessage = _inputMessage.asStateFlow()

    private suspend fun initSocketIO(){
        try {
            socketHandler.socket = IO.socket(SocketHandler.SOCKET_URL)
            socketHandler.socket?.connect()
        }
        catch(e: Error){
            e.printStackTrace()
        }



        socketHandler.socket?.on(SocketHandler.CHAT_KEYS.NEW_MESSAGE){ args ->
            args?.let { d ->
                //val d = data[0]
                if(d.toString().isNotEmpty()){
                    val msg = Gson().fromJson(d[0].toString(), Message::class.java)
                    Log.d("Inside View Model", msg.toString())
                    val newList = _listOfMsgs.value + msg
                    _listOfMsgs.value = newList

                    Log.d("Inside View Model", listOfMsgs.value.toString())
                }

            }
        }
    }

    fun changeInputMessage(newMsg: String){
        _inputMessage.value = newMsg
    }

    fun onSendClicked(){
        if(username.equals("SOCKETIO") && _inputMessage.value.isNotEmpty()){
            val localMessage = Message(
                username = username,
                timeStamp = "",
                msg = "Very Well ${_inputMessage.value}, Enjoy your conversation!!"
            )
            username = _inputMessage.value
            _inputMessage.value = ""
            val newList = _listOfMsgs.value + localMessage
            _listOfMsgs.value = newList

            viewModelScope.launch(Dispatchers.IO) {
                initSocketIO()
            }

        }
        else if(_inputMessage.value.isNotEmpty()){

            val sendMessage = Message(username = username, "12:00",inputMessage.value)

            viewModelScope.launch(Dispatchers.IO) {
                Log.d("Inside ViewModel","Message not sent")
                socketHandler.emitChat(sendMessage)
                Log.d("Inside ViewModel","Message Sent")
            }

            _inputMessage.value = ""
        }
    }

}