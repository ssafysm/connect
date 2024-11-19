package com.ssafy.smartstore_jetpack.presentation.views.signin.login

import com.ssafy.smartstore_jetpack.presentation.views.signin.join.InputValidState

data class LoginUiState(
    val userIdValidState: InputValidState = InputValidState.NONE,
    val userPassValidState: InputValidState = InputValidState.NONE
) {
    val isLoginBtnEnable: Boolean =
        ((userIdValidState == InputValidState.VALID) && (userPassValidState == InputValidState.VALID))
}