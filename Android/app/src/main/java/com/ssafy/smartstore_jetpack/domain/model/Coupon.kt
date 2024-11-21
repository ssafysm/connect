package com.ssafy.smartstore_jetpack.domain.model

data class Coupon(
    val id: String,
    val userId: String,
    val name: String,
    val image: String,
    val couponTime: String,
    val price: String
)