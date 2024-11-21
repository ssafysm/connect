package com.ssafy.smartstore_jetpack.presentation.views.main.coupon

import com.ssafy.smartstore_jetpack.domain.model.Coupon

interface CouponClickListener {

    fun onClickCoupon(coupon: Coupon)
}