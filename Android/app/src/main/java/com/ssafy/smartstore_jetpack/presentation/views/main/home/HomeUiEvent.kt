package com.ssafy.smartstore_jetpack.presentation.views.main.home

sealed class HomeUiEvent {

    data object GoToLogin : HomeUiEvent()

    data object GoToJoin : HomeUiEvent()

    data object GoToNotice : HomeUiEvent()

    data object GoToOrderDetail : HomeUiEvent()

    data object GoToChatting : HomeUiEvent()

    data object GetUserInfo : HomeUiEvent()
}