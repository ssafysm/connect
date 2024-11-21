package com.ssafy.smartstore_jetpack.data.api

import com.ssafy.smartstore_jetpack.domain.model.Comment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CommentApi {

    @POST("rest/comment")
    suspend fun postComment(
        @Body comment: Comment
    ): Response<Boolean>

    @PUT("rest/comment")
    suspend fun putComment(
        @Body comment: Comment
    ): Response<Boolean>

    @DELETE("rest/comment/{id}")
    suspend fun deleteComment(
        @Path("id") id: Int
    ): Response<Boolean>
}