package com.ssafy.smartstore_jetpack.data.repository.comment

import com.ssafy.smartstore_jetpack.domain.model.Comment
import retrofit2.Response

interface CommentRemoteDataSource {

    suspend fun postComment(comment: Comment): Response<Boolean>

    suspend fun putComment(comment: Comment): Response<Boolean>

    suspend fun deleteComment(id: Int): Response<Boolean>
}