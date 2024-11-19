package com.ssafy.smartstore_jetpack.domain.model

data class Order(
    val id: Int,
    val userId: String,
    val orderTable: String,
    val orderTime: String,
    val completed: String,
    val details: List<OrderDetail> = emptyList()
)