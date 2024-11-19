package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetCookieUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend fun setLoginCookie(cookies: HashSet<String>) {
        dataStoreRepository.setLoginCookie(cookies)
    }

    suspend fun deleteLoginCookie() {
        dataStoreRepository.deleteLoginCookie()
    }
}