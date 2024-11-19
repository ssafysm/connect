package com.ssafy.smartstore_jetpack.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class OrderResponse(
    @Json(name = "id")
    val orderId: Int = 0,

    @Json(name = "userId")
    val userId: String = "",

    @Json(name = "orderTable")
    val orderTable: String = "",

    @Json(name = "orderTime")
    val orderDate: Date = Date(),

    @Json(name = "completed")
    val orderCompleted: Char = 'N',

    @Json(name = "details")
    var details:List<OrderDetailResponse> = emptyList(),

    var totalPrice: Int = 0,
    var orderCount: Int = 0
)

@JsonClass(generateAdapter = true)
data class OrderDetailResponse(
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
    val unitPrice: Int,

    @Json(name = "sumPrice")
    val sumPrice: Int,

    @Json(name = "type")
    val productType: String
)