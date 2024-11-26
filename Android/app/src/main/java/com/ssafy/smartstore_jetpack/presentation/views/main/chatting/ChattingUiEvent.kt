package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

sealed class ChattingUiEvent {

    data object SelectImage : ChattingUiEvent()

    data object SendMessageFail : ChattingUiEvent()

    data object SendMessage : ChattingUiEvent()

    data object GoToMenu : ChattingUiEvent()

    data class ChatPlanDelete(val isSuccess: Boolean) : ChattingUiEvent()

    data object GoToShop : ChattingUiEvent()

    data object GoToPlan : ChattingUiEvent()

    data object GoToPlan2 : ChattingUiEvent()

    data object GoToPlanImage : ChattingUiEvent()

    data object SubmitPlanImage : ChattingUiEvent()

    data object SubmitPlanText : ChattingUiEvent()

    data object SubmitPlanProgress : ChattingUiEvent()

    data object SubmitPlanFinish : ChattingUiEvent()
}