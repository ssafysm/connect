package com.ssafy.smartstore_jetpack.data.repository.event

import com.ssafy.smartstore_jetpack.data.api.EventApi
import com.ssafy.smartstore_jetpack.data.entity.EventEntity
import retrofit2.Response
import javax.inject.Inject

class EventRemoteDataSourceImpl @Inject constructor(
    private val eventApi: EventApi
) : EventRemoteDataSource {

    override suspend fun getEvents(): Response<List<EventEntity>> =
        eventApi.getEvents()
}