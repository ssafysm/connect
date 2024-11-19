package com.ssafy.smartstore_jetpack.domain.repository

import com.ssafy.smartstore_jetpack.domain.model.Comment
import com.ssafy.smartstore_jetpack.domain.model.Result

interface CommentRepository {

    suspend fun postComment(comment: Comment): Result<Boolean>

    suspend fun putComment(comment: Comment): Result<Boolean>

    suspend fun deleteComment(id: Int): Result<Boolean>
}