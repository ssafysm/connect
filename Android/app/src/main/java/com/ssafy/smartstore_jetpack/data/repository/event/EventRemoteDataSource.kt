package com.ssafy.smartstore_jetpack.data.repository.event

import com.ssafy.smartstore_jetpack.data.entity.EventEntity
import retrofit2.Response

interface EventRemoteDataSource {

    suspend fun getEvents(): Response<List<EventEntity>>
}