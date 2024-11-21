// FcmApi.kt
package com.ssafy.smartstore_jetpack.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FcmApi {

	@POST("rest/fcm/add")
	suspend fun addFcmToken(
		@Query("userId") userId: String,
		@Query("fcmToken") fcmToken: String
	): Response<Boolean>

	@POST("rest/fcm/remove")
	suspend fun removeFcmToken(
		@Query("userId") userId: String,
		@Query("fcmToken") fcmToken: String
	): Response<Boolean>

	@GET("rest/fcm/tokens")
	suspend fun getTokens(
		@Query("userId") userId: String
	): Response<List<String>>
}
