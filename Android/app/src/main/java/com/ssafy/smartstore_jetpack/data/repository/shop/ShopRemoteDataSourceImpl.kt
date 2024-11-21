package com.ssafy.smartstore_jetpack.data.repository.shop

import com.ssafy.smartstore_jetpack.data.api.ShopApi
import com.ssafy.smartstore_jetpack.data.entity.ShopEntity
import retrofit2.Response
import javax.inject.Inject

class ShopRemoteDataSourceImpl @Inject constructor(
    private val shopApi: ShopApi
) : ShopRemoteDataSource {

    override suspend fun getShops(): Response<List<ShopEntity>> =
        shopApi.getShops()
}