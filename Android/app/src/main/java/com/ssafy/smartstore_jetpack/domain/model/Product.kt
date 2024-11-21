package com.ssafy.smartstore_jetpack.domain.model

data class Product(
    val id: Int,
    val name: String,
    val type: String,
    val price: String,
    val img: String,
    val description: String,
    val mode: String,
    val comments: List<Comment>,
    val productCommentTotalCnt: Int?,
    val productRatingAvg: String?,
    val productTotalSellCnt: Int?
)