package com.ssafy.smartstore_jetpack.domain.model

data class UserInfo(
    val grade: Grade,
    val user: User,
    val orders: List<Order>
)
