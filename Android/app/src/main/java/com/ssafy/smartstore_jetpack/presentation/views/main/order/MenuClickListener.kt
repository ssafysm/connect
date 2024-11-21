package com.ssafy.smartstore_jetpack.presentation.views.main.order

import android.view.View
import com.ssafy.smartstore_jetpack.domain.model.Product

interface MenuClickListener {

    fun onClickSomeProduct(product: Product, sharedView: View)
}