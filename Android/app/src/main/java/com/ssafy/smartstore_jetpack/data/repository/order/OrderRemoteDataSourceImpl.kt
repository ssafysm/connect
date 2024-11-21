package com.ssafy.smartstore_jetpack.data.repository.order

import com.ssafy.smartstore_jetpack.data.api.OrderApi
import com.ssafy.smartstore_jetpack.data.entity.OrderEntity
import com.ssafy.smartstore_jetpack.domain.model.Order
import retrofit2.Response
import javax.inject.Inject

class OrderRemoteDataSourceImpl @Inject constructor(
    private val orderApi: OrderApi
) : OrderRemoteDataSource {

    override suspend fun postOrder(order: Order): Response<Int> = orderApi.postOrder(order)

    override suspend fun getOrderDetail(orderId: Int): Response<OrderEntity> = orderApi.getOrderDetail(orderId)

    override suspend fun getLastMonthOrders(id: String): Response<List<OrderEntity>> = orderApi.getLastMonthOrders(id)

    override suspend fun getLast6MonthOrders(id: String): Response<List<OrderEntity>> = orderApi.getLast6MonthOrders(id)
}