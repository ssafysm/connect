package com.ssafy.smartstore_jetpack.presentation.views.main.order

sealed class OrderUiEvent {

    data object GoToMenuDetail : OrderUiEvent()

    data object GoToCart : OrderUiEvent()
}