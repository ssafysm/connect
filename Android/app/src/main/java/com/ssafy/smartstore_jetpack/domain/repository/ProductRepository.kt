package com.ssafy.smartstore_jetpack.domain.repository

import com.ssafy.smartstore_jetpack.domain.model.Product
import com.ssafy.smartstore_jetpack.domain.model.Result

interface ProductRepository {

    suspend fun getProducts(): Result<List<List<Product>>>

    suspend fun getProductWithComment(productId: Int): Result<Product>
}