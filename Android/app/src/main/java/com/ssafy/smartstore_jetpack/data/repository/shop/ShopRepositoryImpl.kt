package com.ssafy.smartstore_jetpack.data.repository.shop

import com.ssafy.smartstore_jetpack.data.mapper.ShopsMapper
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.model.Shop
import com.ssafy.smartstore_jetpack.domain.repository.ShopRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val shopRemoteDataSource: ShopRemoteDataSource
) : ShopRepository {

    override suspend fun getShops(): Result<List<Shop>> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                shopRemoteDataSource.getShops()
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(ShopsMapper(body))
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }
}