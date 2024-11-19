package com.ssafy.smartstore_jetpack.data.repository.comment

import com.ssafy.smartstore_jetpack.data.api.CommentApi
import com.ssafy.smartstore_jetpack.domain.model.Comment
import retrofit2.Response
import javax.inject.Inject

class CommentRemoteDataSourceImpl @Inject constructor(
    private val commentApi: CommentApi
) : CommentRemoteDataSource {

    override suspend fun postComment(comment: Comment): Response<Boolean> = commentApi.postComment(comment)

    override suspend fun putComment(comment: Comment): Response<Boolean> = commentApi.putComment(comment)

    override suspend fun deleteComment(id: Int): Response<Boolean> = commentApi.deleteComment(id)
}