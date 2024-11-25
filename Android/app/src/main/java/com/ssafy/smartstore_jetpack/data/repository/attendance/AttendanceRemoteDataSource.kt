package com.ssafy.smartstore_jetpack.data.repository.attendance

import retrofit2.Response

interface AttendanceRemoteDataSource {

    suspend fun postAttendanceMark(userId: String, year: Int, month: Int, day: Int): Response<Boolean>

    suspend fun getAttendances(userId: String, year: Int, month: Int): Response<List<Boolean>>
}