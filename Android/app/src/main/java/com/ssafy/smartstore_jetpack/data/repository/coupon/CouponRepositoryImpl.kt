package com.ssafy.smartstore_jetpack.data.repository.coupon

import com.ssafy.smartstore_jetpack.data.mapper.CouponsMapper
import com.ssafy.smartstore_jetpack.domain.model.Coupon
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.CouponRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CouponRepositoryImpl @Inject constructor(
    private val couponRemoteDataSource: CouponRemoteDataSource
) : CouponRepository {

    override suspend fun getCoupons(userId: String): Result<List<Coupon>> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                couponRemoteDataSource.getCoupons(userId)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(CouponsMapper(body))
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }

    override suspend fun deleteCoupon(couponId: String): Result<Boolean> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                couponRemoteDataSource.deleteCoupon(couponId)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(body)
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }
}