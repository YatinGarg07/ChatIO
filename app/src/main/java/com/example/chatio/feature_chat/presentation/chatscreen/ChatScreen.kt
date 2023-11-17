package com.example.chatio.feature_chat.presentation.chatscreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatio.feature_chat.presentation.MainViewModel
import com.example.chatio.feature_chat.presentation.util.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

val list = listOf<Message>(
    Message(
        "Yatin",
        "",
        "lajfojsdoifisojf o ijo jo sofjo ijsoijf  oij  oi jo  jfiodji jij"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: MainViewModel
) {
    val msgs by viewModel.listOfMsgs.collectAsState()
    val inputMessage by viewModel.inputMessage.collectAsState()

    Column {
        val state = rememberLazyListState()
        LaunchedEffect(key1 = msgs){
            state.animateScrollToItem(msgs.size-1)
        }

        // Message Area
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            state = state
        )
        {
            msgs.forEachIndexed { index, message ->
                item(){
                    MessageBox(
                        alignment = if(message.username == viewModel.username) Alignment.CenterEnd else Alignment.CenterStart,
                        msg = message
                    )
                }
            }

        }

        //Input TextField & Send Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = inputMessage,
                onValueChange = { newMessage ->
                    viewModel.changeInputMessage(newMsg = newMessage)
                },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(text = "Type Your Message")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences)
            )
            FilledIconButton(
                onClick = {
                          viewModel.onSendClicked()
                },
                modifier = Modifier.padding(6.dp)
            ) {
                Icon(imageVector = Icons.Filled.Send, contentDescription = "Send")
            }


        }

    }


}

@Composable
fun MessageBox(
    msg: Message,
    alignment: Alignment
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .align(alignment)
                .padding(16.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color(0XFFF3E8FD))
                .widthIn(min = 20.dp, max = 230.dp)
                .padding(15.dp)
        ) {
            Text(
                text = msg.username,
                style= MaterialTheme.typography.titleMedium
            )
            Text(
                text = msg.msg,
                modifier = Modifier.padding(top = 10.dp),
                softWrap = true,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }


}


@Composable
@Preview
fun PreviewMessageBox() {
//    MessageBox(
//        Message("Sender", "", "")
//    )
}