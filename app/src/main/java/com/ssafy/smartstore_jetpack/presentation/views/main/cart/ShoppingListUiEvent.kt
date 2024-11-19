package com.ssafy.smartstore_jetpack.presentation.views.main.cart

sealed class ShoppingListUiEvent {

    data object ShopOrder : ShoppingListUiEvent()

    data object Tagged : ShoppingListUiEvent()

    data object TakeOutOrder : ShoppingListUiEvent()

    data object FinishOrder : ShoppingListUiEvent()

    data object OrderFail : ShoppingListUiEvent()
}
