package com.ssafy.smartstore_jetpack.data.mapper

import com.ssafy.smartstore_jetpack.data.entity.UserEntity
import com.ssafy.smartstore_jetpack.domain.model.Stamp
import com.ssafy.smartstore_jetpack.domain.model.User

object UserMapper {

    operator fun invoke(userEntity: UserEntity): User {
        val newStamps = mutableListOf<Stamp>()

        userEntity.stampList.forEach { stamp ->
            newStamps.add(
                Stamp(
                    id = stamp.id,
                    userId = stamp.userId,
                    orderId = stamp.orderId,
                    quantity = stamp.quantity
                )
            )
        }

        return User(
            id = userEntity.id,
            name = userEntity.name,
            pass = userEntity.pass,
            stamps = userEntity.stamps.toString(),
            stampList = newStamps,
            alarmMode = userEntity.alarmMode,
            appTheme = userEntity.appTheme
        )
    }
}