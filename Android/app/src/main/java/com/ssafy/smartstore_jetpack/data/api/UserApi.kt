package com.ssafy.smartstore_jetpack.data.api

import com.ssafy.smartstore_jetpack.data.entity.UserEntity
import com.ssafy.smartstore_jetpack.data.entity.UserInfoEntity
import com.ssafy.smartstore_jetpack.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApi {

    @POST("rest/user")
    suspend fun postUser(
        @Body body: User
    ): Response<Boolean>

    @POST("rest/user/login")
    suspend fun postUserForLogin(
        @Body body: User
    ): Response<UserEntity>

    @GET("rest/user/info")
    suspend fun getUserInfo(
        @Query("id") id: String
    ): Response<UserInfoEntity>

    @POST("rest/user/info")
    suspend fun postUserInfo(
        @Body body: User
    ): Response<UserInfoEntity>

    @GET("rest/user/isUsed")
    suspend fun getIsUserId(
        @Query("id") id: String
    ): Response<Boolean>

    @PUT("rest/user/password")
    suspend fun putPassword(
        @Body body: User
    ): Response<Boolean>

    @PUT("rest/user/alarmMode")
    suspend fun putAlarmMode(
        @Body body: User
    ): Response<Boolean>

    @PUT("rest/user/appTheme")
    suspend fun putAppTheme(
        @Body body: User
    ): Response<Boolean>
}