package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

import com.ssafy.smartstore_jetpack.presentation.util.InputValidState

data class ChattingUiState(
    val chatMessageValidState: InputValidState = InputValidState.NONE
) {
    val isSubmitBtnEnable: Boolean = (chatMessageValidState == InputValidState.VALID)
}