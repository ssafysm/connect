package com.ssafy.smartstore_jetpack.presentation.views.main.attendance

sealed class AttendanceUiEvent {

    data object getBeacon : AttendanceUiEvent()
}