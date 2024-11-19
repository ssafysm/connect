package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetUserIdUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend fun setUserId(userId: String) {
        dataStoreRepository.setUserId(userId)
    }
}