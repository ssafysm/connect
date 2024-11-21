package com.ssafy.smartstore_jetpack.presentation.views.main.password

sealed class PasswordUiEvent {

    data object PasswordUpdateSuccess : PasswordUiEvent()

    data object PasswordUpdateFailed: PasswordUiEvent()
}