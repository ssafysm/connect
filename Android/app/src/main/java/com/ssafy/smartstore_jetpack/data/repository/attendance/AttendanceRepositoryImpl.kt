package com.ssafy.smartstore_jetpack.data.repository.attendance

import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.AttendanceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val attendanceRemoteDataSource: AttendanceRemoteDataSource
) : AttendanceRepository {

    override suspend fun postAttendanceMark(
        userId: String,
        year: Int,
        month: Int,
        day: Int
    ): Result<Boolean> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                attendanceRemoteDataSource.postAttendanceMark(userId, year, month, day)
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

    override suspend fun getAttendances(
        userId: String,
        year: Int,
        month: Int
    ): Result<List<Boolean>> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                attendanceRemoteDataSource.getAttendances(userId, year, month)
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
}