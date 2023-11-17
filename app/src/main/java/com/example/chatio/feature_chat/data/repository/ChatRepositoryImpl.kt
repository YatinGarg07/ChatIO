package com.example.chatio.feature_chat.data.repository

import com.example.chatio.feature_chat.data.data_source.MessageDao
import com.example.chatio.feature_chat.domain.model.Message
import com.example.chatio.feature_chat.domain.repository.ChatRepository
import io.socket.client.Socket
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val dao: MessageDao
) : ChatRepository {
    override fun getMessages(): Flow<List<Message>> {
      return dao.getNotes()
    }

    override suspend fun insertMessage(msg: Message) {
        dao.insertMessage(msg)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }


}