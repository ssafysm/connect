package com.ssafy.smartstore_jetpack.data.repository.fcm

import com.ssafy.smartstore_jetpack.data.api.FcmApi
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.model.Status
import javax.inject.Inject

class FcmRepository @Inject constructor(
	private val fcmApi: FcmApi
) {
	suspend fun addFcmToken(userId: String, fcmToken: String): Result<Boolean> {
		return try {
			val response = fcmApi.addFcmToken(userId, fcmToken)
			if (response.isSuccessful) {
				Result(Status.SUCCESS, response.body(), null)
			} else {
				Result(Status.FAIL, null, response.message())
			}
		} catch (e: Exception) {
			Result(Status.FAIL, null, e.message)
		}
	}

}