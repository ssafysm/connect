package com.ssafy.smartstore_jetpack.domain.model

data class ShoppingCart(
    val menuId: Int,
    val menuImg: String,
    val menuName: String,
    val menuCnt: String,
    val menuPrice: String,
    val totalPrice: String,
    val type: String
)