package com.example.chatio.feature_chat.domain.repository

import com.example.chatio.feature_chat.domain.model.Message
import kotlinx.coroutines.flow.Flow


interface ChatRepository {

    fun getMessages(): Flow<List<Message>>

    suspend fun insertMessage(msg : Message)

    //suspend fun sendMessage(msg: Message)

    suspend fun deleteAll()

}