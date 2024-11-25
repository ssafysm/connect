package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.model.Product
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend fun getProducts(): Result<List<List<Product>>> {
        return productRepository.getProducts()
    }

    suspend fun getProductWithComment(productId: Int): Result<Product> {
        return productRepository.getProductWithComment(productId)
    }

    suspend fun getProductTop5(): Result<String> {
        return productRepository.getProductTop5()
    }
}