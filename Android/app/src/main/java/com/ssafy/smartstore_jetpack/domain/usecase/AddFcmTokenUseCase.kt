// AddFcmTokenUseCase.kt
package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.data.repository.fcm.FcmRepository
import com.ssafy.smartstore_jetpack.domain.model.Result
import javax.inject.Inject

class AddFcmTokenUseCase @Inject constructor(
	private val fcmRepository: FcmRepository
) {
	suspend fun addFcmToken(userId: String, fcmToken: String): Result<Boolean> {
		return fcmRepository.addFcmToken(userId, fcmToken)
	}
}
