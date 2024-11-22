package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetNoticesUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend fun setNotices(notices: List<String>) {
        dataStoreRepository.setNotices(notices.toHashSet())
    }
}