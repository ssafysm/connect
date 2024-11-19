package com.ssafy.smartstore_jetpack.presentation.views.main.cart

interface ShoppingListClickListener {

    fun onClickNFCTrue()

    fun onClickNFCFalse()

    fun onClickTakeOutFinish()

    fun onClickProductDelete(position: Int)

    fun onClickOrder()
}