package com.ssafy.smartstore_jetpack.data.api

import com.ssafy.smartstore_jetpack.data.entity.ShopEntity
import retrofit2.Response
import retrofit2.http.GET

interface ShopApi {

    @GET("rest/shop")
    suspend fun getShops(): Response<List<ShopEntity>>
}