package com.example.chatio.feature_chat.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    val username: String,
    val timeStamp: String = "00:00",
    val msg: String,
    @PrimaryKey val id: Int? = null
) {


}
