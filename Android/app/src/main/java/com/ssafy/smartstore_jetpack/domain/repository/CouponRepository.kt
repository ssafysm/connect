package com.ssafy.smartstore_jetpack.domain.repository

import com.ssafy.smartstore_jetpack.domain.model.Coupon
import com.ssafy.smartstore_jetpack.domain.model.Result

interface CouponRepository {

    suspend fun getCoupons(userId: String): Result<List<Coupon>>

    suspend fun deleteCoupon(couponId: String): Result<Boolean>
}