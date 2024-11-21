package com.ssafy.smartstore_jetpack.domain.model

data class Comment(
    val id: Int,
    val userId: String,
    val productId: Int,
    val rating: Float,
    val comment: String,
    val userName: String
)