package com.ssafy.smartstore_jetpack.data.repository.product

import com.ssafy.smartstore_jetpack.data.api.ProductApi
import com.ssafy.smartstore_jetpack.data.entity.ProductEntity
import retrofit2.Response
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val productApi: ProductApi
) : ProductRemoteDataSource {

    override suspend fun getProducts(): Response<List<ProductEntity>> = productApi.getProducts()

    override suspend fun getProductWithComment(productId: Int): Response<ProductEntity> =
        productApi.getProductWithComments(productId)
}