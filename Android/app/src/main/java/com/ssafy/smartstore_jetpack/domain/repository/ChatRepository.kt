package com.ssafy.smartstore_jetpack.domain.repository

import com.ssafy.smartstore_jetpack.domain.model.ChatImageRequest
import com.ssafy.smartstore_jetpack.domain.model.ChatProgressRequest
import com.ssafy.smartstore_jetpack.domain.model.ChatTextRequest
import com.ssafy.smartstore_jetpack.domain.model.GPTMenu
import com.ssafy.smartstore_jetpack.domain.model.Result

interface ChatRepository {

    suspend fun submitChatImage(chatImageRequest: ChatImageRequest): Result<GPTMenu>

    suspend fun submitChatText(chatTextRequest: ChatTextRequest): Result<GPTMenu>

    suspend fun submitProgress(userId: String, progress: ChatProgressRequest): Result<GPTMenu>

    suspend fun deleteChatPlan(userId: String): Result<GPTMenu>
}