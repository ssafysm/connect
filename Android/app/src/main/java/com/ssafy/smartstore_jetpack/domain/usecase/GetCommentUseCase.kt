package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.model.Comment
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.CommentRepository
import javax.inject.Inject

class GetCommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) {

    suspend fun postComment(comment: Comment): Result<Boolean> {
        return commentRepository.postComment(comment)
    }

    suspend fun putComment(comment: Comment): Result<Boolean> {
        return commentRepository.putComment(comment)
    }

    suspend fun deleteComment(id: Int): Result<Boolean> {
        return commentRepository.deleteComment(id)
    }
}