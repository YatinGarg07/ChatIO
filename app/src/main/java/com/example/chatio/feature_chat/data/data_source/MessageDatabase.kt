package com.example.chatio.feature_chat.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chatio.feature_chat.domain.model.Message

@Database(
    entities = [Message::class],
    version = 1
)
abstract class MessageDatabase : RoomDatabase(){

    abstract val messageDao: MessageDao
    companion object{
        const val DATABASE_NAME = "notes_db"
    }

}