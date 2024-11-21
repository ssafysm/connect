package com.ssafy.smartstore_jetpack.presentation.views.main.menudetail

sealed class MenuDetailUiEvent {

    data object SelectRating : MenuDetailUiEvent()

    data object SubmitComment : MenuDetailUiEvent()

    data object UpdateComment : MenuDetailUiEvent()

    data object DeleteComment : MenuDetailUiEvent()

    data object UpdateCommentFail : MenuDetailUiEvent()

    data object DeleteCommentFail : MenuDetailUiEvent()

    data object SelectProduct : MenuDetailUiEvent()
}