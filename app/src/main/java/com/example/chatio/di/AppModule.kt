package com.example.chatio.di

import android.util.Log
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
    fun provideSocket() : Socket? {
        return try {
            IO.socket("http://chat.socket.io")
        } catch (e: URISyntaxException) {
            Log.d("Inside App Module", "Invalid Socket")
            null
        }
    }

}

