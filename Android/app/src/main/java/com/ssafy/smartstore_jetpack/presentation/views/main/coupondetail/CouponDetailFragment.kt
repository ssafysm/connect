package com.ssafy.smartstore_jetpack.presentation.views.main.coupondetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentCouponDetailBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.util.BlurHelper
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
        is CouponDetailUiEvent.CouponTakeOut -> {
            findNavController().navigateSafely(R.id.action_coupon_detail_to_shop_select)
            BlurHelper.applyBlur(binding.fragmentCouponDetail, 20F)
        }

        is CouponDetailUiEvent.FinishCouponOrder -> {
            Toast.makeText(
                requireContext(),
                "주문에 성공했어요! 주문 번호는 ${event.orderId}번이에요.",
                Toast.LENGTH_SHORT
            ).show()
            requireActivity().supportFragmentManager.popBackStack()
            BlurHelper.clearBlur(binding.fragmentCouponDetail)
        }

        is CouponDetailUiEvent.CouponOrderFail -> {
            Toast.makeText(requireContext(), "주문에 실패했어요ㅠㅠ", Toast.LENGTH_SHORT).show()
            BlurHelper.clearBlur(binding.fragmentCouponDetail)
        }
    }
}