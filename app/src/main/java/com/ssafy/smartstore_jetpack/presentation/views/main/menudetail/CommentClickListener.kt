package com.ssafy.smartstore_jetpack.presentation.views.main.menudetail

import com.ssafy.smartstore_jetpack.domain.model.Comment

interface CommentClickListener {

    fun onClickSelectRating()

    fun onClickInsertComment()

    fun onClickCommentCancel()

    fun onClickUpdateComment(comment: Comment)

    fun onClickDeleteComment(comment: Comment)
}