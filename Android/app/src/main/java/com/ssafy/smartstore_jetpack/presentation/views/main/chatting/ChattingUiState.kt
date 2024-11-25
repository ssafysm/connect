package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

import com.ssafy.smartstore_jetpack.presentation.util.InputValidState

data class ChattingUiState(
    val chatMessageValidState: InputValidState = InputValidState.NONE,
    val isSendValidState: InputValidState = InputValidState.VALID,
    val chatImageValidState: InputValidState = InputValidState.NONE,
    val buttonsValidateState: InputValidState = InputValidState.NONE,
    val buttonsEnableState: InputValidState = InputValidState.VALID,
    val planSecondTextMode: InputValidState = InputValidState.NONE
) {
    val isSubmitBtnEnable: Boolean = ((chatMessageValidState == InputValidState.VALID) && (isSendValidState == InputValidState.VALID))
    val isEditTextEnable: Boolean = (isSendValidState == InputValidState.VALID)
    val isChatImageEnable: Boolean = (chatImageValidState == InputValidState.VALID)
    val isFourButtonsVisible: Boolean = (buttonsValidateState == InputValidState.VALID)
    val isFourButtonsEnable: Boolean = (buttonsEnableState == InputValidState.VALID)
    val isPlanSecondTextMode: Boolean = (planSecondTextMode == InputValidState.VALID)
}