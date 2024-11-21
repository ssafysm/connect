package com.ssafy.smartstore_jetpack.presentation.views.main.coupondetail

sealed class CouponDetailUiEvent {

    data object CouponTakeOut : CouponDetailUiEvent()

    data class FinishCouponOrder(val orderId: Int) : CouponDetailUiEvent()

    data object CouponOrderFail : CouponDetailUiEvent()
}
