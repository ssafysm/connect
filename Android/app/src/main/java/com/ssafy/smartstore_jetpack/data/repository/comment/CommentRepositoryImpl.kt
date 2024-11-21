package com.ssafy.smartstore_jetpack.data.repository.comment

import com.ssafy.smartstore_jetpack.domain.model.Comment
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.CommentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentRemoteDataSource: CommentRemoteDataSource
) : CommentRepository {

    override suspend fun postComment(comment: Comment): Result<Boolean> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                commentRemoteDataSource.postComment(comment)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(body)
            } else {
                Result.error(response.errorBody().toString(), null)
            }

        } catch (e: Exception) {
            Result.fail()
        }


    override suspend fun putComment(comment: Comment): Result<Boolean> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                commentRemoteDataSource.putComment(comment)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(body)
            } else {
                Result.error(response.errorBody().toString(), null)
            }

        } catch (e: Exception) {
            Result.fail()
        }

    override suspend fun deleteComment(id: Int): Result<Boolean> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                commentRemoteDataSource.deleteComment(id)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(body)
            } else {
                Result.error(response.errorBody().toString(), null)
            }

        } catch (e: Exception) {
            Result.fail()
        }
}