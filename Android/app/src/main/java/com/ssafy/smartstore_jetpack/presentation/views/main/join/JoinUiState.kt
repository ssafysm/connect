package com.ssafy.smartstore_jetpack.presentation.views.main.join

import com.ssafy.smartstore_jetpack.presentation.util.DuplicateState
import com.ssafy.smartstore_jetpack.presentation.util.IdState
import com.ssafy.smartstore_jetpack.presentation.util.InputValidState
import com.ssafy.smartstore_jetpack.presentation.util.PasswordState

data class JoinUiState(
    val joinIdValidState: IdState = IdState.INIT,
    val joinPassValidState: PasswordState = PasswordState.INIT,
    val joinPassConfirmValidState: PasswordState = PasswordState.INIT,
    val joinNameValidState: InputValidState = InputValidState.NONE,
    val joinIdDuplicateState: DuplicateState = DuplicateState.DUPLICATE
) {
    val isJoinBtnEnable: Boolean =
        ((joinIdDuplicateState == DuplicateState.NONE) && (joinIdValidState == IdState.VALID) && (joinPassValidState == PasswordState.VALID) && (joinPassConfirmValidState == PasswordState.VALID) && (joinNameValidState == InputValidState.VALID))
    val isIdCheckBtnState: Boolean = (joinIdDuplicateState == DuplicateState.NONE)
    val isIdCheckBtnEnable: Boolean = (joinIdValidState == IdState.VALID)
}