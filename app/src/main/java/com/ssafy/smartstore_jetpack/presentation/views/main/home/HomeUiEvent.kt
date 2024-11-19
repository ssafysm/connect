package com.ssafy.smartstore_jetpack.presentation.views.main.home

sealed class HomeUiEvent {

    data object GoToNotice : HomeUiEvent()

    data object GoToOrderDetail : HomeUiEvent()
}