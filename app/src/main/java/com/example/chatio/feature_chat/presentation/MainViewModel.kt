package com.example.chatio.feature_chat.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val socket : Socket?
): ViewModel() {


}