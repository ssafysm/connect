package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

sealed class ChattingUiEvent {

    data object SelectImage : ChattingUiEvent()

    data object SendMessageFail : ChattingUiEvent()

    data object SendMessage : ChattingUiEvent()
}