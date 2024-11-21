package com.ssafy.smartstore_jetpack.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.ssafy.smartstore_jetpack.domain.model.ShoppingCart

@JsonClass(generateAdapter = true)
data class UserInfoEntity(
    @Json(name = "grade")
    val grade: GradeEntity,

    @Json(name = "user")
    val user: UserEntity,

    @Json(name = "order")
    val orders: List<OrderEntity>
)

@JsonClass(generateAdapter = true)
data class GradeEntity(
    @Json(name = "title")
    val title: String,

    @Json(name = "img")
    val img: String,

    @Json(name = "step")
    val step: Int?,

    @Json(name = "to")
    val to: Int?,

    @Json(name = "stepMax")
    val stepMax: Int
)

@JsonClass(generateAdapter = true)
data class UserEntity(
    @Json(name = "id")
    val id: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "pass")
    val pass: String,

    @Json(name = "stamps")
    val stamps: Int,

    @Json(name = "stampList")
    val stampList: List<StampEntity>
)

@JsonClass(generateAdapter = true)
data class StampEntity(
    @Json(name = "id")
    val id: Int,

    @Json(name = "userId")
    val userId: String,

    @Json(name = "orderId")
    val orderId: Int,

    @Json(name = "quantity")
    val quantity: Int,
)

@JsonClass(generateAdapter = true)
data class OrderEntity(
    @Json(name = "id")
    val id: Int,

    @Json(name = "userId")
    val userId: String,

    @Json(name = "orderTable")
    val orderTable: String,

    @Json(name = "orderTime")
    val orderTime: String,

    @Json(name = "completed")
    val completed: String,

    @Json(name = "details")
    val details: List<OrderDetailEntity>?
)

@JsonClass(generateAdapter = true)
data class OrderDetailEntity(
    @Json(name = "id")
    val id: Int,

    @Json(name = "orderId")
    val orderId: Int,

    @Json(name = "productId")
    val productId: Int,

    @Json(name = "quantity")
    val quantity: Int,

    @Json(name = "name")
    val productName: String,

    @Json(name = "img")
    val productImg: String,

    @Json(name = "unitPrice")
    val unitPrice: String,

    @Json(name = "sumPrice")
    val sumPrice: Int,

    @Json(name = "type")
    val productType: String
)

@JsonClass(generateAdapter = true)
data class ProductEntity(
    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "type")
    val type: String,

    @Json(name = "price")
    val price: Int,

    @Json(name = "img")
    val img: String,

    @Json(name = "description")
    val description: String?,

    @Json(name = "mode")
    val mode: String?,

    @Json(name = "commentCount")
    val productCommentTotalCnt: Int?,

    @Json(name = "averageStars")
    val productRatingAvg: Double?,

    @Json(name = "totalSells")
    val productTotalSellCnt: Int?,

    @Json(name = "comments")
    val comments: List<CommentEntity>?
)

@JsonClass(generateAdapter = true)
data class CommentEntity(
    @Json(name = "id")
    val id: Int,

    @Json(name = "userId")
    val userId: String,

    @Json(name = "productId")
    val productId: Int,

    @Json(name = "rating")
    val rating: Float,

    @Json(name = "comment")
    val comment: String,

    @Json(name = "userName")
    val userName: String = ""
)

@JsonClass(generateAdapter = true)
data class ShopEntity(
    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String?,

    @Json(name = "image")
    val image: String?,

    @Json(name = "description")
    val description: String?,

    @Json(name = "time")
    val time: String?,

    @Json(name = "latitude")
    val latitude: Double?,

    @Json(name = "longitude")
    val longitude: Double?
)

@JsonClass(generateAdapter = true)
data class EventEntity(
    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String?,

    @Json(name = "image")
    val image: String?,

    @Json(name = "url")
    val url: String?
)

@JsonClass(generateAdapter = true)
data class CouponEntity(
    @Json(name = "id")
    val id: Int,

    @Json(name = "user_id")
    val userId: String?,

    @Json(name = "name")
    val name: String?,

    @Json(name = "image")
    val image: String?,

    @Json(name = "coupon_time")
    val couponTime: String?,

    @Json(name = "price")
    val price: Int?
)