package com.ssafy.smartstore_jetpack.data.repository.chat

import com.ssafy.smartstore_jetpack.data.api.ChatApi
import com.ssafy.smartstore_jetpack.data.entity.GPTPlanEntity
import com.ssafy.smartstore_jetpack.domain.model.ChatImageRequest
import com.ssafy.smartstore_jetpack.domain.model.ChatProgressRequest
import com.ssafy.smartstore_jetpack.domain.model.ChatTextRequest
import retrofit2.Response
import javax.inject.Inject

class ChatRemoteDataSourceImpl @Inject constructor(
    private val chatApi: ChatApi
) : ChatRemoteDataSource {

    override suspend fun postChatImage(chatImageRequest: ChatImageRequest): Response<GPTPlanEntity> =
        chatApi.postChatImage(chatImageRequest)

    override suspend fun postChatText(chatTextRequest: ChatTextRequest): Response<GPTPlanEntity> =
        chatApi.postChatText(chatTextRequest)

    override suspend fun postProgress(userId: String, progress: ChatProgressRequest): Response<GPTPlanEntity> =
        chatApi.postChatProgress(userId, progress)
}