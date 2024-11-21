package com.ssafy.smartstore_jetpack.data.mapper

import com.ssafy.smartstore_jetpack.data.entity.CouponEntity
import com.ssafy.smartstore_jetpack.domain.model.Coupon

object CouponsMapper {

    operator fun invoke(couponEntities: List<CouponEntity>): List<Coupon> {
        val newCoupons = mutableListOf<Coupon>()

        couponEntities.forEach { couponEntity ->
            newCoupons.add(
                Coupon(
                    id = couponEntity.id.toString(),
                    userId = couponEntity.userId ?: "",
                    name = couponEntity.name ?: "",
                    image = couponEntity.image ?: "",
                    couponTime = couponEntity.couponTime ?: "",
                    price = when (couponEntity.price) {
                        null -> "0"

                        else -> couponEntity.price.toString()
                    }
                )
            )
        }

        return newCoupons
    }
}