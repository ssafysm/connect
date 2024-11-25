package com.ssafy.smartstore_jetpack.domain.model

data class ChatImageRequest(
    val userId: String,
    val level: Int,
    val base64Image: String
)