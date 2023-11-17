package com.example.chatio.feature_chat.presentation

import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatio.feature_chat.domain.repository.ChatRepository
import com.example.chatio.feature_chat.presentation.util.Message
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val socketHandler: SocketHandler,
    private val repository: ChatRepository
): ViewModel() {


    private val _listOfMsgs : MutableStateFlow<List<Message>> =
        MutableStateFlow(
            listOf(Message(username = "SOCKETIO", timeStamp = "", msg = "What's Your Name"))
        )
    val listOfMsgs = _listOfMsgs.asStateFlow()

    var username = "SOCKET-IO"
    val socket = socketHandler

    private val _inputMessage = MutableStateFlow("")
    val inputMessage = _inputMessage.asStateFlow()

    private fun initSocketIO(){
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

                    viewModelScope.launch {
                        // Scope For Improvement
                        val newMessage = com.example.chatio.feature_chat.domain.model.Message(username = msg.username, timeStamp = msg.timeStamp, msg = msg.msg)
                        repository.insertMessage(newMessage)
                    }

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
        if(username.equals("SOCKET-IO") && _inputMessage.value.isNotEmpty()){
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

        if(inputMessage.value.equals("Get All")){
            //Log.d("In ViewModel, OnSendClicked", inputMessage.equals("Get All").toString())

            viewModelScope.launch(Dispatchers.IO) {

                val newList = repository.getMessages().collect(){

                   val localList =  it.map {
                       Message(username = it.username, timeStamp =  it.timeStamp, msg = it.msg)
                    }
                    Log.d("OnSendClicked, new List", localList.toString())

                    _listOfMsgs.value = localList
                }
                _inputMessage.value = ""

            }

        }
        else if(inputMessage.value.equals("Delete All")){

            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteAll()
                _listOfMsgs.value = emptyList()
                _inputMessage.value = ""
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