package com.ssafy.smartstore_jetpack.data.api

import com.ssafy.smartstore_jetpack.data.entity.ProductEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("rest/product")
    suspend fun getProducts(): Response<List<ProductEntity>>

    @GET("rest/product/{productId}")
    suspend fun getProductWithComments(
        @Path("productId") productId: Int
    ): Response<ProductEntity>

    @GET("rest/product/gpt-summary")
    suspend fun getProductTop5(): Response<String>
}