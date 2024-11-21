package com.ssafy.smartstore_jetpack.data.repository.coupon

import com.ssafy.smartstore_jetpack.data.api.CouponApi
import com.ssafy.smartstore_jetpack.data.entity.CouponEntity
import retrofit2.Response
import javax.inject.Inject

class CouponRemoteDataSourceImpl @Inject constructor(
    private val couponApi: CouponApi
) : CouponRemoteDataSource{

    override suspend fun getCoupons(userId: String): Response<List<CouponEntity>> =
        couponApi.getCoupons(userId)
}