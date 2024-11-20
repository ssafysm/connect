package com.ssafy.smartstore_jetpack.presentation.views.main.login

sealed class LoginUiEvent {

    data object GoToLogin : LoginUiEvent()

    data object LoginFail : LoginUiEvent()

    data object GoToJoin : LoginUiEvent()
}