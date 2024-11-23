package com.ssafy.smartstore_jetpack.presentation.views.main.menudetail

import com.ssafy.smartstore_jetpack.presentation.util.InputValidState

data class MenuDetailUiState(
    val commentValidState: InputValidState = InputValidState.NONE
) {
    val isEnrollBtnEnable: Boolean = (commentValidState == InputValidState.VALID)
}