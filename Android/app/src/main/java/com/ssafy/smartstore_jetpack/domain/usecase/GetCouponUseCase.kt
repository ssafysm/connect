package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.model.Coupon
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.CouponRepository
import javax.inject.Inject

class GetCouponUseCase @Inject constructor(
    private val couponRepository: CouponRepository
) {

    suspend fun getCoupons(userId: String): Result<List<Coupon>> =
        couponRepository.getCoupons(userId)

    suspend fun deleteCoupon(couponId: String): Result<Boolean> =
        couponRepository.deleteCoupon(couponId)
}