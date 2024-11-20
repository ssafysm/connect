package com.ssafy.smartstore_jetpack.presentation.views.main.join

import com.ssafy.smartstore_jetpack.presentation.util.DuplicateState
import com.ssafy.smartstore_jetpack.presentation.util.InputValidState
import com.ssafy.smartstore_jetpack.presentation.util.PasswordState

data class JoinUiState(
    val joinIdValidState: InputValidState = InputValidState.NONE,
    val joinPassValidState: PasswordState = PasswordState.INIT,
    val joinPassConfirmValidState: PasswordState = PasswordState.INIT,
    val joinNameValidState: InputValidState = InputValidState.NONE,
    val joinIdDuplicateState: DuplicateState = DuplicateState.DUPLICATE
) {
    val isJoinBtnEnable: Boolean =
        ((joinIdDuplicateState == DuplicateState.NONE) && (joinIdValidState == InputValidState.VALID) && (joinPassValidState == PasswordState.VALID) && (joinPassConfirmValidState == PasswordState.VALID) && (joinNameValidState == InputValidState.VALID))
    val isIdCheckBtnState: Boolean = (joinIdDuplicateState == DuplicateState.NONE)
    val isIdCheckBtnEnable: Boolean = (joinIdValidState == InputValidState.VALID)
}