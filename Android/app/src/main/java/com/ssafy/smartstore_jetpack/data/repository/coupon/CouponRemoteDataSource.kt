package com.ssafy.smartstore_jetpack.data.repository.coupon

import com.ssafy.smartstore_jetpack.data.entity.CouponEntity
import retrofit2.Response

interface CouponRemoteDataSource {

    suspend fun getCoupons(userId: String): Response<List<CouponEntity>>

    suspend fun deleteCoupon(couponId: String): Response<Boolean>
}