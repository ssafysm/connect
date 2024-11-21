package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppThemeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    fun getAppTheme(): Flow<String> = dataStoreRepository.getAppTheme()
}