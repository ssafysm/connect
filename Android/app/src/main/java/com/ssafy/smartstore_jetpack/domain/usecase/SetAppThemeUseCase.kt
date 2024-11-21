package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetAppThemeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend fun setAppTheme(theme: String) {
        dataStoreRepository.setAppTheme(theme)
    }
}