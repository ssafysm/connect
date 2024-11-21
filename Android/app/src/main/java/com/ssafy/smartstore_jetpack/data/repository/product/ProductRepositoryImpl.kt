package com.ssafy.smartstore_jetpack.data.repository.product

import com.ssafy.smartstore_jetpack.data.mapper.ProductMapper
import com.ssafy.smartstore_jetpack.data.mapper.ProductsMapper
import com.ssafy.smartstore_jetpack.domain.model.Product
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                productRemoteDataSource.getProducts()
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(ProductsMapper(body))
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }

    override suspend fun getProductWithComment(productId: Int): Result<Product> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                productRemoteDataSource.getProductWithComment(productId)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(ProductMapper(body))
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }
}