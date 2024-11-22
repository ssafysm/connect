package com.ssafy.smartstore_jetpack.data.repository.alarm

import com.ssafy.smartstore_jetpack.data.mapper.AlarmsMapper
import com.ssafy.smartstore_jetpack.domain.model.Alarm
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.AlarmRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmRemoteDataSource: AlarmRemoteDataSource
) : AlarmRepository {

    override suspend fun getAlarms(userId: String): Result<List<Alarm>> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                alarmRemoteDataSource.getAlarms(userId)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(AlarmsMapper(body))
            } else {
                Result.error(response.errorBody().toString(), null)
            }

        } catch (e: Exception) {
            Result.fail()
        }
}