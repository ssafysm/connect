package com.ssafy.smartstore_jetpack.data.repository.user

import com.ssafy.smartstore_jetpack.data.entity.UserEntity
import com.ssafy.smartstore_jetpack.data.entity.UserInfoEntity
import com.ssafy.smartstore_jetpack.domain.model.User
import retrofit2.Response

interface UserRemoteDataSource {

    suspend fun postUser(user: User): Response<Boolean>

    suspend fun getUserInfo(id: String): Response<UserInfoEntity>

    suspend fun postUserInfo(user: User): Response<UserInfoEntity>

    suspend fun getIsUsedId(id: String): Response<Boolean>

    suspend fun postUserForLogin(user: User): Response<UserEntity>

    suspend fun putPassword(user: User): Response<Boolean>

    suspend fun putAlarmMode(user: User): Response<Boolean>

    suspend fun putAppTheme(user: User): Response<Boolean>
}