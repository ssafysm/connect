package com.ssafy.smartstore_jetpack.presentation.views.main.my

sealed class MyPageUiEvent {

    data object GoToOrderDetail : MyPageUiEvent()

    data object GoToSettings : MyPageUiEvent()

    data object DoLogout : MyPageUiEvent()
}