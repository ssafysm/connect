package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.model.Order
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.OrderRepository
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {

    suspend fun makeOrder(order: Order): Result<Int> {
        return orderRepository.postOrder(order)
    }

    suspend fun getOrderDetail(orderId: Int): Result<Order> {
        return orderRepository.getOrderDetail(orderId)
    }

    suspend fun getLastMonthOrders(id: String): Result<List<Order>> {
        return orderRepository.getLastMonthOrders(id)
    }

    suspend fun getLast6MonthOrders(id: String): Result<List<Order>> {
        return orderRepository.getLast6MonthOrders(id)
    }
}