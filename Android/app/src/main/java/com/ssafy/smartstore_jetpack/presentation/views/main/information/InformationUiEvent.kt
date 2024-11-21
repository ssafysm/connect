package com.ssafy.smartstore_jetpack.presentation.views.main.information

sealed class InformationUiEvent {

    data object GoToPassword : InformationUiEvent()
}