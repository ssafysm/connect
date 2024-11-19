package com.ssafy.smartstore_jetpack.presentation.views.main.setting

sealed class SettingUiEvent {

    data object SelectAppTheme : SettingUiEvent()

    data class SubmitAppTheme(val theme: String) : SettingUiEvent()

    data object CloseAppTheme : SettingUiEvent()
}
