package com.ssafy.smartstore_jetpack.presentation.views.main.coupondetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentCouponDetailBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.util.BlurHelper.applyBlur
import com.ssafy.smartstore_jetpack.presentation.util.BlurHelper.clearBlur
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CouponDetailFragment :
    BaseFragment<FragmentCouponDetailBinding>(R.layout.fragment_coupon_detail) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        collectLatestFlow(viewModel.couponDetailUiEvent) { handleUiEvent(it) }
    }

    private fun handleUiEvent(event: CouponDetailUiEvent) = when (event) {
        is CouponDetailUiEvent.CouponShop -> {
            findNavController().navigateSafely(R.id.action_coupon_detail_to_shop_select)
            applyBlur(binding.fragmentCouponDetail, 20F)
        }

        is CouponDetailUiEvent.CouponTakeOut -> {
            findNavController().navigateSafely(R.id.action_coupon_detail_to_shop_select)
            applyBlur(binding.fragmentCouponDetail, 20F)
        }

        is CouponDetailUiEvent.FinishCouponOrder -> {
            showToastMessage("${getString(R.string.message_order_success_start)} ${event.orderId}${getString(
                R.string.message_order_success_end
            )}")
            requireActivity().supportFragmentManager.popBackStack()
            clearBlur(binding.fragmentCouponDetail)
        }

        is CouponDetailUiEvent.CouponOrderFail -> {
            showToastMessage(getString(R.string.message_order_fail))
            clearBlur(binding.fragmentCouponDetail)
        }

        else -> {}
    }
}