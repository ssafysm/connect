package com.ssafy.smartstore_jetpack.presentation.views.main.my

import com.ssafy.smartstore_jetpack.domain.model.Order

interface MyPageClickListener {

    fun onClickOrder(order: Order)

    fun onClickSettings()

    fun onClickHistory()

    fun onClickInformation()

    fun onClickCoupon()

    fun onClickPay()

    fun onClickLogout()
}