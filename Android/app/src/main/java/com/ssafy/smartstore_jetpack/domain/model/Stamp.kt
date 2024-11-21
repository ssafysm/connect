package com.ssafy.smartstore_jetpack.domain.model

data class Stamp(
    val id: Int,
    val userId: String,
    val orderId: Int,
    val quantity: Int,
)