package com.ssafy.smartstore_jetpack.data.mapper

import com.ssafy.smartstore_jetpack.data.entity.CouponEntity
import com.ssafy.smartstore_jetpack.domain.model.Coupon
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.dateFormatAnother

object CouponsMapper {

    operator fun invoke(couponEntities: List<CouponEntity>): List<Coupon> {
        val newCoupons = mutableListOf<Coupon>()

        couponEntities.forEach { couponEntity ->
            newCoupons.add(
                Coupon(
                    id = couponEntity.id.toString(),
                    userId = couponEntity.userId ?: "",
                    name = couponEntity.name ?: "",
                    description = couponEntity.description ?: "",
                    image = couponEntity.image ?: "",
                    iat = dateFormatAnother(couponEntity.iat),
                    exp = dateFormatAnother(couponEntity.exp),
                    menuId = when (couponEntity.menuId) {
                        null -> "1"

                        else -> couponEntity.menuId.toString()
                    },
                    menuCount = when (couponEntity.menuCount) {
                        null -> "0"

                        else -> couponEntity.menuCount.toString()
                    }
                )
            )
        }

        return newCoupons
    }
}