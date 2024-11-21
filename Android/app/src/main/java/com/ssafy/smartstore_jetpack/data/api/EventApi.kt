package com.ssafy.smartstore_jetpack.data.api

import com.ssafy.smartstore_jetpack.data.entity.EventEntity
import retrofit2.Response
import retrofit2.http.GET

interface EventApi {

    @GET("rest/event")
    suspend fun getEvents(): Response<List<EventEntity>>
}