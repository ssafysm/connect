package com.ssafy.smartstore_jetpack.presentation.views.main.cart

data class ShoppingListUiState(
    val shoppingListState: EmptyState = EmptyState.EMPTY
) {
    val isOrderBtnEnable: Boolean = (shoppingListState == EmptyState.NONE)
}

enum class EmptyState {
    EMPTY, NONE
}