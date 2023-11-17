package com.example.chatio.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.chatio.feature_chat.data.data_source.MessageDao
import com.example.chatio.feature_chat.data.data_source.MessageDatabase
import com.example.chatio.feature_chat.data.repository.ChatRepositoryImpl
import com.example.chatio.feature_chat.domain.repository.ChatRepository
import com.example.chatio.feature_chat.presentation.SocketHandler
import com.example.chatio.feature_chat.presentation.util.Message
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSocketHandler() : SocketHandler {
        return SocketHandler()
    }

    @Provides
    @Singleton
    fun provideMessageDatabase(app: Application): MessageDatabase{
        return  Room.databaseBuilder(
            app,
            MessageDatabase::class.java,
            MessageDatabase.DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideChatRepository(db: MessageDatabase) : ChatRepository{
        return ChatRepositoryImpl(db.messageDao)
    }



}

