package com.ssafy.smartstore_jetpack.presentation.views.main.coupon

import com.ssafy.smartstore_jetpack.presentation.util.EmptyState

data class CouponUiState(
    val couponsValidState: EmptyState = EmptyState.EMPTY
) {
    val isEmptyCoupons: Boolean = (couponsValidState == EmptyState.NONE)
}
