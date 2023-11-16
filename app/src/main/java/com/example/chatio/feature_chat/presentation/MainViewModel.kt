package com.example.chatio.feature_chat.presentation

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
    private val _listOfMsgs : MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())
    val listOfMsgs = _listOfMsgs.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            socketHandler.socket = IO.socket(SocketHandler.SOCKET_URL)
            socketHandler.socket?.connect()
        }

        socketHandler.socket?.on(SocketHandler.CHAT_KEYS.NEW_MESSAGE){ args ->
            args?.let { data ->
                if(data.toString().isNotEmpty()){
                    val msg = Gson().fromJson<Message>(data.toString(), Message::class.java)
                    _listOfMsgs.value = _listOfMsgs.value + msg
                }

            }
        }

    }

}