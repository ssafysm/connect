package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import com.ssafy.smartstore_jetpack.domain.model.Shop

interface ShoppingListClickListener {

    fun onClickShop()

    fun onClickTakeout()

    fun onClickShopClose()

    fun onClickShopSelect(shop: Shop)

    fun onClickShopSelectInMap(shop: Shop)

    fun onClickShopSelectCancel()

    fun onClickShoppingFinish()

    fun onClickProductAdd(position: Int)

    fun onClickProductRemove(position: Int)

    fun onClickProductDelete(position: Int)

    fun onClickOrder()
}