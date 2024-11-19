package com.ssafy.smartstore_jetpack.presentation.views.main.password

import com.ssafy.smartstore_jetpack.presentation.util.PasswordState

data class PasswordUiState(
    val newPasswordValidState: PasswordState = PasswordState.INIT,
    val newPasswordConfirmValidState: PasswordState = PasswordState.INIT
) {
    val isUpdateBtnEnable: Boolean =
        ((newPasswordValidState == PasswordState.VALID) && (newPasswordConfirmValidState == PasswordState.VALID))
}
