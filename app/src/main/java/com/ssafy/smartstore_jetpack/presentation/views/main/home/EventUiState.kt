package com.ssafy.smartstore_jetpack.presentation.views.main.home

import android.graphics.drawable.Drawable
import java.util.UUID

sealed class EventUiState(val id: String = UUID.randomUUID().toString()) {

    data class EventItem(
        val eventImage: String
    ) : EventUiState()
}
