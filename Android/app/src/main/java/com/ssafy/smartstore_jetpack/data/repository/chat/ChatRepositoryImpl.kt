package com.ssafy.smartstore_jetpack.data.repository.chat

import com.ssafy.smartstore_jetpack.data.mapper.GPTPlanMapper
import com.ssafy.smartstore_jetpack.domain.model.ChatImageRequest
import com.ssafy.smartstore_jetpack.domain.model.ChatProgressRequest
import com.ssafy.smartstore_jetpack.domain.model.ChatTextRequest
import com.ssafy.smartstore_jetpack.domain.model.GPTMenu
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.ChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatRemoteDataSource: ChatRemoteDataSource
) : ChatRepository {

    override suspend fun submitChatImage(chatImageRequest: ChatImageRequest): Result<GPTMenu> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                chatRemoteDataSource.postChatImage(chatImageRequest)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(GPTPlanMapper(body))
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }

    override suspend fun submitChatText(chatTextRequest: ChatTextRequest): Result<GPTMenu> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                chatRemoteDataSource.postChatText(chatTextRequest)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(GPTPlanMapper(body))
            } else {
                Result.error(response.errorBody().toString(), null)
            }

        } catch (e: Exception) {
            Result.fail()
        }

    override suspend fun submitProgress(userId: String, progress: ChatProgressRequest): Result<GPTMenu> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                chatRemoteDataSource.postProgress(userId, progress)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(GPTPlanMapper(body))
            } else {
                response.code()
                Result.error(response.code().toString(), null)
            }

        } catch (e: Exception) {
            Result.fail()
        }

    override suspend fun deleteChatPlan(userId: String): Result<GPTMenu> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                chatRemoteDataSource.deleteChatPlan(userId)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(GPTPlanMapper(body))
            } else {
                response.code()
                Result.error(response.code().toString(), null)
            }

        } catch (e: Exception) {
            Result.fail()
        }
}