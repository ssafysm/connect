package com.ssafy.smartstore_jetpack.presentation.views.main.cart

sealed class ShoppingListUiEvent {

    data object ShopOrder : ShoppingListUiEvent()

    data object NeedTagging : ShoppingListUiEvent()

    data object Tagged : ShoppingListUiEvent()

    data object TakeOutOrder : ShoppingListUiEvent()

    data class FinishOrder(val orderId: Int) : ShoppingListUiEvent()

    data object OrderFail : ShoppingListUiEvent()
}
