package com.ssafy.smartstore_jetpack.presentation.views.main.order

import com.ssafy.smartstore_jetpack.domain.model.Product

interface ProductClickListener {

    fun onClickProduct(product: Product)

    fun onClickProductToCart()

    fun onClickProductCountUp()

    fun onClickProductCountDown()

    fun onClickToCart()
}