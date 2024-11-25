package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetAlarmReceiveModeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend fun setAlarmReceiveMode(flag: Boolean) {
        dataStoreRepository.setAlarmReceiveMode(flag)
    }
}