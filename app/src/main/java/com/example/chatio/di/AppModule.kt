package com.example.chatio.di

import android.util.Log
import com.example.chatio.feature_chat.presentation.SocketHandler
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

}

