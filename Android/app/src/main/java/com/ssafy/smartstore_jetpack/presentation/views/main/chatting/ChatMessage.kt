package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

import android.net.Uri

data class ChatMessage(
	val text: String,
	val imageUri: Uri?,
	val isSender: Boolean,
	val senderName: String
)
