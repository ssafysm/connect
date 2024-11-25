package com.ssafy.smartstore_jetpack.domain.repository

import com.ssafy.smartstore_jetpack.domain.model.Result

interface AttendanceRepository {

    suspend fun postAttendanceMark(userId: String, year: Int, month: Int, day: Int): Result<Boolean>

    suspend fun getAttendances(userId: String, year: Int, month: Int): Result<List<Boolean>>
}