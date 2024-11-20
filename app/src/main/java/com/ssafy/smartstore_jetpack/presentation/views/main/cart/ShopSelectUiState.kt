package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import com.ssafy.smartstore_jetpack.presentation.util.ShopSelectValidState

data class ShopSelectUiState(
    val selectValidState: ShopSelectValidState = ShopSelectValidState.SEARCH
) {
    val isSearchMode: Boolean = (selectValidState == ShopSelectValidState.SEARCH)
    val isMapMode: Boolean = (selectValidState == ShopSelectValidState.MAP)
    val isSearchSelectMode: Boolean = (selectValidState == ShopSelectValidState.SEARCHSELECT)
    val isMapSelectMode: Boolean = (selectValidState == ShopSelectValidState.MAPSELECT)
}