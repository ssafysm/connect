package com.ssafy.smartstore_jetpack.domain.model

data class Shop(
    val id: String,
    val name: String,
    val image: String,
    val description: String,
    val time: String,
    val latitude: Double,
    val longitude: Double,
    val distance: Float
)