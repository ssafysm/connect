package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.AttendanceRepository
import javax.inject.Inject

class GetAttendanceUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository
) {

    suspend fun postAttendanceMark(userId: String, year: Int, month: Int, day: Int): Result<Boolean> =
        attendanceRepository.postAttendanceMark(userId, year, month, day)

    suspend fun getAttendances(userId: String, year: Int, month: Int): Result<List<Boolean>> =
        attendanceRepository.getAttendances(userId, year, month)
}