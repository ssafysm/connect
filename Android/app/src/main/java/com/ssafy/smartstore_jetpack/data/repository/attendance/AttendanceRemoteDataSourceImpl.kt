package com.ssafy.smartstore_jetpack.data.repository.attendance

import com.ssafy.smartstore_jetpack.data.api.AttendanceApi
import retrofit2.Response
import javax.inject.Inject

class AttendanceRemoteDataSourceImpl @Inject constructor(
    private val attendanceApi: AttendanceApi
) : AttendanceRemoteDataSource {

    override suspend fun postAttendanceMark(
        userId: String,
        year: Int,
        month: Int,
        day: Int
    ): Response<Boolean> = attendanceApi.postAttendanceMark(userId, year, month, day)

    override suspend fun getAttendances(
        userId: String,
        year: Int,
        month: Int
    ): Response<List<Boolean>> = attendanceApi.getAttendances(userId, year, month)
}