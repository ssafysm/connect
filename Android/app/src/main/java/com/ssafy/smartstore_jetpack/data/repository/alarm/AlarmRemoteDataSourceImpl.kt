package com.ssafy.smartstore_jetpack.data.repository.alarm

import com.ssafy.smartstore_jetpack.data.api.AlarmApi
import com.ssafy.smartstore_jetpack.data.entity.AlarmEntity
import retrofit2.Response
import javax.inject.Inject

class AlarmRemoteDataSourceImpl @Inject constructor(
    private val alarmApi: AlarmApi
) : AlarmRemoteDataSource {

    override suspend fun getAlarms(userId: String): Response<List<AlarmEntity>> =
        alarmApi.getAlarms(userId)
}