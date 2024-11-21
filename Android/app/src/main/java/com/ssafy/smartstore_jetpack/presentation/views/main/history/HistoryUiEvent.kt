package com.ssafy.smartstore_jetpack.presentation.views.main.history

sealed class HistoryUiEvent {

    data object GoToOrderDetail : HistoryUiEvent()
}