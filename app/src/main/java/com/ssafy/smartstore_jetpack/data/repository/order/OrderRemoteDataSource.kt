package com.ssafy.smartstore_jetpack.data.repository.order

import com.ssafy.smartstore_jetpack.data.entity.OrderEntity
import com.ssafy.smartstore_jetpack.domain.model.Order
import retrofit2.Response

interface OrderRemoteDataSource {

    suspend fun postOrder(order: Order): Response<Int>

    suspend fun getOrderDetail(orderId: Int): Response<OrderEntity>

    suspend fun getLastMonthOrders(id: String): Response<List<OrderEntity>>

    suspend fun getLast6MonthOrders(id: String): Response<List<OrderEntity>>
}