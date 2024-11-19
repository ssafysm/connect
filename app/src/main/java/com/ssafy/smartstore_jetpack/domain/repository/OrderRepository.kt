package com.ssafy.smartstore_jetpack.domain.repository

import com.ssafy.smartstore_jetpack.domain.model.Order
import com.ssafy.smartstore_jetpack.domain.model.Result

interface OrderRepository {

    suspend fun postOrder(order: Order): Result<Int>

    suspend fun getOrderDetail(orderId: Int): Result<Order>

    suspend fun getLastMonthOrders(id: String): Result<List<Order>>

    suspend fun getLast6MonthOrders(id: String): Result<List<Order>>
}