package com.ssafy.smartstore_jetpack.domain.model

data class Coupon(
    val id: String,
    val userId: String,
    val name: String,
    val description: String,
    val image: String,
    val iat: String,
    val exp: String,
    val menuId: String,
    val menuCount: String
)