package com.ssafy.smartstore_jetpack.presentation.views.main.password

import com.ssafy.smartstore_jetpack.presentation.util.InputValidState

data class PasswordUiState(
    val newPasswordValidState: InputValidState = InputValidState.NONE,
    val newPasswordConfirmValidState: InputValidState = InputValidState.NONE
) {

}
