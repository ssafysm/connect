package com.ssafy.smartstore_jetpack.presentation.views.main.home

import com.ssafy.smartstore_jetpack.domain.model.Order

interface HomeClickListener {

    fun onClickHomeSignUp()

    fun onClickHomeLogin()

    fun onClickNotice()

    fun onClickOrderDetail(order: Order)
}