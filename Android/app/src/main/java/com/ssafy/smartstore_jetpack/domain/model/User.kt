package com.ssafy.smartstore_jetpack.domain.model

data class User(
    val id: String,
    val name: String,
    val pass: String,
    val stamps: String,
    val stampList: List<Stamp>,
    val alarmMode: Boolean,
    val appTheme: Int
)