package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import com.ssafy.smartstore_jetpack.presentation.util.EmptyState
import com.ssafy.smartstore_jetpack.presentation.util.SelectState

data class ShoppingListUiState(
    val shoppingListState: EmptyState = EmptyState.EMPTY,
    val shopSelectState: SelectState = SelectState.NONE
) {
    val isOrderBtnEnable: Boolean = (shoppingListState == EmptyState.NONE)
    val isShopSelecting: Boolean = (shopSelectState == SelectState.SELECT)
}