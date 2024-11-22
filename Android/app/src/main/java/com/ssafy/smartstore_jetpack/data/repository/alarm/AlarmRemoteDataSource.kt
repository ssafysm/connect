package com.ssafy.smartstore_jetpack.data.repository.alarm

import com.ssafy.smartstore_jetpack.data.entity.AlarmEntity
import retrofit2.Response

interface AlarmRemoteDataSource {

    suspend fun getAlarms(userId: String): Response<List<AlarmEntity>>
}