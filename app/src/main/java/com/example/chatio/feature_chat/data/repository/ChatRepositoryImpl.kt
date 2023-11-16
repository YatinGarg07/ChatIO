package com.example.chatio.feature_chat.data.repository

import com.example.chatio.feature_chat.domain.repository.ChatRepository
import com.example.chatio.feature_chat.presentation.util.Message
import io.socket.client.Socket
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    val socket: Socket
) : ChatRepository {
    override fun getChats(): Flow<List<Message>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertChat(msg: Message) {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(msg: Message) {
        TODO("Not yet implemented")
    }

    override suspend fun getMessage(): Message {
        TODO("Not yet implemented")
    }


}