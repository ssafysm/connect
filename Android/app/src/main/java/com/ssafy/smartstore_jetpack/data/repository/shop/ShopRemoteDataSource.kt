package com.ssafy.smartstore_jetpack.data.repository.shop

import com.ssafy.smartstore_jetpack.data.entity.ShopEntity
import retrofit2.Response

interface ShopRemoteDataSource {

    suspend fun getShops(): Response<List<ShopEntity>>
}