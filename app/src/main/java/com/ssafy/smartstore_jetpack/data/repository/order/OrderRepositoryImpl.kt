package com.ssafy.smartstore_jetpack.data.repository.order

import com.ssafy.smartstore_jetpack.data.entity.OrderResponse
import com.ssafy.smartstore_jetpack.data.mapper.OrderMapper
import com.ssafy.smartstore_jetpack.data.mapper.OrdersMapper
import com.ssafy.smartstore_jetpack.domain.model.Order
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderRemoteDataSource: OrderRemoteDataSource
) : OrderRepository {

    override suspend fun postOrder(order: Order): Result<Int> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                orderRemoteDataSource.postOrder(order)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(body)
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }

    override suspend fun getOrderDetail(orderId: Int): Result<Order> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                orderRemoteDataSource.getOrderDetail(orderId)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(OrderMapper(body))
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }

    override suspend fun getLastMonthOrders(id: String): Result<List<Order>> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                orderRemoteDataSource.getLast6MonthOrders(id)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(OrdersMapper(body))
            } else {
                Timber.d(response.errorBody().toString())
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Timber.d("Exception: $e")
            Result.fail()
        }

    override suspend fun getLast6MonthOrders(id: String): Result<List<Order>> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                orderRemoteDataSource.getLast6MonthOrders(id)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(OrdersMapper(body))
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }
}