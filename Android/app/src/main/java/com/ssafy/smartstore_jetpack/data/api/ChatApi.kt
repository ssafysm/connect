package com.ssafy.smartstore_jetpack.data.api

import com.ssafy.smartstore_jetpack.data.entity.GPTPlanEntity
import com.ssafy.smartstore_jetpack.domain.model.ChatImageRequest
import com.ssafy.smartstore_jetpack.domain.model.ChatProgressRequest
import com.ssafy.smartstore_jetpack.domain.model.ChatTextRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApi {

    @POST("rest/chat/upload")
    suspend fun postChatImage(
        @Body chatImage: ChatImageRequest
    ): Response<GPTPlanEntity>

    @POST("rest/chat/text")
    suspend fun postChatText(
        @Body chatText: ChatTextRequest
    ): Response<GPTPlanEntity>

    @POST("rest/chat/progress/{userId}")
    suspend fun postChatProgress(
        @Path("userId") userId: String,
        @Body progress: ChatProgressRequest
    ): Response<GPTPlanEntity>

    @DELETE("rest/chat/{userId}")
    suspend fun deleteChatPlan(
        @Path("userId") userId: String
    ): Response<GPTPlanEntity>
}