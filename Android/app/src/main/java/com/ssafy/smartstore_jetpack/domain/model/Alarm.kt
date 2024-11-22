package com.ssafy.smartstore_jetpack.domain.model

data class Alarm(
    val id: String,
    val userId: String,
    val title: String,
    val content: String,
    val sendTime: String
)