package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.model.User
import com.ssafy.smartstore_jetpack.domain.model.UserInfo
import com.ssafy.smartstore_jetpack.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun postUser(user: User): Result<Boolean> {
        return userRepository.postUser(user)
    }

    suspend fun getUserInfo(id: String): Result<UserInfo> {
        return userRepository.getUserInfo(id)
    }

    suspend fun getIsUsedId(id: String): Result<Boolean> {
        return userRepository.getIsUsedId(id)
    }

    suspend fun postUserForLogin(user: User): Result<User> {
        return userRepository.postUserForLogin(user)
    }
}