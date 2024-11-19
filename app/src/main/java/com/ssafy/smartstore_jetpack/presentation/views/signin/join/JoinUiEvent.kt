package com.ssafy.smartstore_jetpack.presentation.views.signin.join

sealed class JoinUiEvent {

    data class CheckId(val isUsedId: Boolean?) : JoinUiEvent()

    data object GoToLogin : JoinUiEvent()

    data object JoinFail : JoinUiEvent()
}