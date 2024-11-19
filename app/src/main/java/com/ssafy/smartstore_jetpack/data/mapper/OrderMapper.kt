package com.ssafy.smartstore_jetpack.data.mapper

import com.ssafy.smartstore_jetpack.data.entity.OrderEntity
import com.ssafy.smartstore_jetpack.domain.model.Order
import com.ssafy.smartstore_jetpack.domain.model.OrderDetail

object OrderMapper {

    operator fun invoke(orderEntity: OrderEntity): Order {
        val newDetails = mutableListOf<OrderDetail>()

        orderEntity.details?.forEach { detail ->
            newDetails.add(
                OrderDetail(
                    id = detail.id,
                    orderId = detail.orderId,
                    productId = detail.productId,
                    quantity = detail.quantity,
                    unitPrice = detail.unitPrice,
                    img = detail.productImg,
                    productName = detail.productName
                )
            )
        }

        return Order(
            id = orderEntity.id,
            userId = orderEntity.userId,
            orderTable = orderEntity.orderTable,
            orderTime = orderEntity.orderTime,
            completed = orderEntity.completed,
            details = newDetails
        )
    }
}