package com.ssafy.smartstore_jetpack.data.api

import com.ssafy.smartstore_jetpack.data.entity.CouponEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CouponApi {

    @GET("rest/coupon/byUser")
    suspend fun getCoupons(
        @Query("userId") userId: String
    ): Response<List<CouponEntity>>
}