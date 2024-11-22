package com.ssafy.smartstore_jetpack.presentation.views.main.notice

sealed class NoticeUiEvent {

    data object DeleteNotice : NoticeUiEvent()
}