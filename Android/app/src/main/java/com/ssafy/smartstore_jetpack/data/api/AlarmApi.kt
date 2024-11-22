package com.ssafy.smartstore_jetpack.data.api

import com.ssafy.smartstore_jetpack.data.entity.AlarmEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlarmApi {

    @GET("rest/alarm/{userId}")
    suspend fun getAlarms(
        @Path("userId") userId: String
    ): Response<List<AlarmEntity>>
}