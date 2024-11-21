package com.ssafy.smartstore_jetpack.data.api

import com.ssafy.smartstore_jetpack.data.entity.OrderEntity
import com.ssafy.smartstore_jetpack.domain.model.Order
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderApi {

    @POST("rest/order")
    suspend fun postOrder(
        @Body body: Order
    ): Response<Int>

    @GET("rest/order/{orderId}")
    suspend fun getOrderDetail(
        @Path("orderId") orderId: Int
    ): Response<OrderEntity>

    @GET("rest/order/byUser")
    suspend fun getLastMonthOrders(
        @Query("id") id: String
    ): Response<List<OrderEntity>>

    @GET("rest/order/byUserIn6Months")
    suspend fun getLast6MonthOrders(
        @Query("id") id: String
    ): Response<List<OrderEntity>>
}