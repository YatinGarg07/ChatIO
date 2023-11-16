package com.example.chatio.feature_chat.presentation.util

data class Message(
    val username: String,
    val timeStamp: String = "00:00",
    val msg: String
)
