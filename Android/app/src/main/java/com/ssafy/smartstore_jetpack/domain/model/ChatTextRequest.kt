package com.ssafy.smartstore_jetpack.domain.model

data class ChatTextRequest(
    val userId: String,
    val level: Int,
    val plan: String
)
