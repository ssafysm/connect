package com.ssafy.smartstore_jetpack.data.mapper

import com.ssafy.smartstore_jetpack.data.entity.UserInfoEntity
import com.ssafy.smartstore_jetpack.domain.model.Grade
import com.ssafy.smartstore_jetpack.domain.model.Order
import com.ssafy.smartstore_jetpack.domain.model.OrderDetail
import com.ssafy.smartstore_jetpack.domain.model.Stamp
import com.ssafy.smartstore_jetpack.domain.model.User
import com.ssafy.smartstore_jetpack.domain.model.UserInfo

object UserInfoMapper {

    operator fun invoke(userInfoEntity: UserInfoEntity): UserInfo {
        val newStamps = mutableListOf<Stamp>()
        val newOrders = mutableListOf<Order>()

        userInfoEntity.user.stampList.forEach { stamp ->
            newStamps.add(
                Stamp(
                    id = stamp.id,
                    userId = stamp.userId,
                    orderId = stamp.orderId,
                    quantity = stamp.quantity
                )
            )
        }

        userInfoEntity.orders.forEach { order ->
            val newDetails = mutableListOf<OrderDetail>()

            order.details?.forEach { detail ->
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

            newOrders.add(
                Order(
                    id = order.id,
                    userId = order.userId,
                    orderTable = order.orderTable,
                    orderTime = order.orderTime,
                    completed = order.completed,
                    details = newDetails.toList()
                )
            )
        }

        return UserInfo(
            grade = Grade(
                img = userInfoEntity.grade.img ?: "",
                step = if (userInfoEntity.grade.step == null) "0" else userInfoEntity.grade.step.toString(),
                stepMax = userInfoEntity.grade.stepMax.toString(),
                to = if (userInfoEntity.grade.to == null) "0" else userInfoEntity.grade.to.toString(),
                title =  userInfoEntity.grade.title
            ),
            user = User(
                id = userInfoEntity.user.id,
                name = userInfoEntity.user.name,
                pass = userInfoEntity.user.pass,
                stamps = userInfoEntity.user.stamps.toString(),
                stampList = newStamps.toList(),
                alarmMode = userInfoEntity.user.alarmMode,
                appTheme = userInfoEntity.user.appTheme
            ),
            orders = newOrders.toList()
        )
    }
}