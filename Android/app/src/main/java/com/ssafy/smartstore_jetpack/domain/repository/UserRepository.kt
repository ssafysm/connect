package com.ssafy.smartstore_jetpack.domain.repository

import com.ssafy.smartstore_jetpack.domain.model.User
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.model.UserInfo

interface UserRepository {

    suspend fun postUser(user: User): Result<Boolean>

    suspend fun getUserInfo(id: String): Result<UserInfo>

    suspend fun postUserInfo(user: User): Result<UserInfo>

    suspend fun getIsUsedId(id: String): Result<Boolean>

    suspend fun postUserForLogin(user: User): Result<User>

    suspend fun putPassword(user: User): Result<Boolean>

    suspend fun putAlarmMode(user: User): Result<Boolean>

    suspend fun putAppTheme(user: User): Result<Boolean>
}