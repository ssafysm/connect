package com.ssafy.smartstore_jetpack.presentation.views.main.notice

import com.ssafy.smartstore_jetpack.presentation.util.EmptyState

data class NoticeUiState(
    val noticesState: EmptyState = EmptyState.EMPTY
) {
    val isNoticesNotEmpty: Boolean = (noticesState == EmptyState.NONE)
}