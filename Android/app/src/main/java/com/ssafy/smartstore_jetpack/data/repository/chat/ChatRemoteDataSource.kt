package com.ssafy.smartstore_jetpack.data.repository.chat

import com.ssafy.smartstore_jetpack.data.entity.GPTPlanEntity
import com.ssafy.smartstore_jetpack.domain.model.ChatImageRequest
import com.ssafy.smartstore_jetpack.domain.model.ChatProgressRequest
import com.ssafy.smartstore_jetpack.domain.model.ChatTextRequest
import retrofit2.Response

interface ChatRemoteDataSource {

    suspend fun postChatImage(chatImageRequest: ChatImageRequest): Response<GPTPlanEntity>

    suspend fun postChatText(chatTextRequest: ChatTextRequest): Response<GPTPlanEntity>

    suspend fun postProgress(userId: String, progress: ChatProgressRequest): Response<GPTPlanEntity>
}