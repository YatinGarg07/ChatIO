package com.example.chatio.feature_chat.domain.repository

import com.example.chatio.feature_chat.presentation.util.Message
import kotlinx.coroutines.flow.Flow


interface ChatRepository {

    fun getChats(): Flow<List<Message>>

    suspend fun insertChat(msg : Message)

    suspend fun sendMessage(msg: Message)

    suspend fun getMessage(): Message

}