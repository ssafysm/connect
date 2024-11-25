package com.ssafy.smartstore_jetpack.data.repository.product

import com.ssafy.smartstore_jetpack.data.entity.ProductEntity
import retrofit2.Response

interface ProductRemoteDataSource {

    suspend fun getProducts(): Response<List<ProductEntity>>

    suspend fun getProductWithComment(productId: Int): Response<ProductEntity>

    suspend fun getProductTop5(): Response<String>
}