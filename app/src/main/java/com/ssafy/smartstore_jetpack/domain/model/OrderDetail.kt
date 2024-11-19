package com.ssafy.smartstore_jetpack.domain.model

data class OrderDetail(
    val id: Int,
    val orderId: Int,
    val productId: Int,
    val quantity: Int,
    val unitPrice: String,
    val img: String,
    val productName: String
)