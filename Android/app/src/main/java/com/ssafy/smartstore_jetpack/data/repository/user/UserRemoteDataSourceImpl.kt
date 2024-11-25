package com.ssafy.smartstore_jetpack.data.repository.user

import com.ssafy.smartstore_jetpack.data.api.UserApi
import com.ssafy.smartstore_jetpack.data.entity.UserEntity
import com.ssafy.smartstore_jetpack.data.entity.UserInfoEntity
import com.ssafy.smartstore_jetpack.domain.model.User
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApi: UserApi
) : UserRemoteDataSource {

    override suspend fun postUser(user: User): Response<Boolean> = userApi.postUser(user)

    override suspend fun getUserInfo(id: String): Response<UserInfoEntity> = userApi.getUserInfo(id)

    override suspend fun postUserInfo(user: User): Response<UserInfoEntity> =
        userApi.postUserInfo(user)

    override suspend fun getIsUsedId(id: String): Response<Boolean> = userApi.getIsUserId(id)

    override suspend fun postUserForLogin(user: User): Response<UserEntity> =
        userApi.postUserForLogin(user)

    override suspend fun putPassword(user: User): Response<Boolean> =
        userApi.putPassword(user)

    override suspend fun putAlarmMode(user: User): Response<Boolean> =
        userApi.putAlarmMode(user)

    override suspend fun putAppTheme(user: User): Response<Boolean> =
        userApi.putAppTheme(user)
}