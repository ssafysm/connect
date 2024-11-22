package com.ssafy.smartstore_jetpack.domain.repository

import com.ssafy.smartstore_jetpack.domain.model.Alarm
import com.ssafy.smartstore_jetpack.domain.model.Result

interface AlarmRepository {

    suspend fun getAlarms(userId: String): Result<List<Alarm>>
}