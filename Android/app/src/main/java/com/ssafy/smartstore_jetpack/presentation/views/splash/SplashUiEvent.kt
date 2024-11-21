package com.ssafy.smartstore_jetpack.presentation.views.splash

sealed class SplashUiEvent {

    data object LoginSuccess : SplashUiEvent()

    data object LoginFailed : SplashUiEvent()
}
