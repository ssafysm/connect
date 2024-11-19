package com.ssafy.smartstore_jetpack.presentation.views.main.my

sealed class MyPageUiEvent {

    data object GoToSettings : MyPageUiEvent()

    data object GoToHistory : MyPageUiEvent()

    data object GoToInformation : MyPageUiEvent()

    data object GoToCoupon : MyPageUiEvent()

    data object GoToPay : MyPageUiEvent()

    data object DoLogout : MyPageUiEvent()
}