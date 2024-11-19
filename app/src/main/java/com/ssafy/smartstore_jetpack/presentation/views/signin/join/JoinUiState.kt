package com.ssafy.smartstore_jetpack.presentation.views.signin.join

data class JoinUiState(
    val joinIdValidState: InputValidState = InputValidState.NONE,
    val joinPassValidState: InputValidState = InputValidState.NONE,
    val joinNameValidState: InputValidState = InputValidState.NONE,
    val joinIdDuplicateState: DuplicateState = DuplicateState.DUPLICATE
) {
    val isJoinBtnEnable: Boolean =
        ((joinIdDuplicateState == DuplicateState.NONE) && (joinIdValidState == InputValidState.VALID) && (joinPassValidState == InputValidState.VALID) && (joinNameValidState == InputValidState.VALID))
    val isIdCheckBtnState: Boolean = (joinIdDuplicateState == DuplicateState.NONE)
}

enum class InputValidState {
    NONE, VALID
}

enum class DuplicateState {
    DUPLICATE, NONE
}