package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.model.ChatImageRequest
import com.ssafy.smartstore_jetpack.domain.model.ChatProgressRequest
import com.ssafy.smartstore_jetpack.domain.model.ChatTextRequest
import com.ssafy.smartstore_jetpack.domain.model.GPTMenu
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.ChatRepository
import javax.inject.Inject

class SetChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    suspend fun submitChatImage(chatImageRequest: ChatImageRequest): Result<GPTMenu> {
        return chatRepository.submitChatImage(chatImageRequest)
    }

    suspend fun submitChatText(chatTextRequest: ChatTextRequest): Result<GPTMenu> {
        return chatRepository.submitChatText(chatTextRequest)
    }

    suspend fun submitProgress(userId: String, progress: ChatProgressRequest): Result<GPTMenu> {
        return chatRepository.submitProgress(userId, progress)
    }
}