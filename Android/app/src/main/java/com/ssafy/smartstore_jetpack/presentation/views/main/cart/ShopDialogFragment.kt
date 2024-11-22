package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentShopDialogBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseDialogFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import com.ssafy.smartstore_jetpack.presentation.views.main.coupondetail.CouponDetailUiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ShopDialogFragment : BaseDialogFragment<FragmentShopDialogBinding>(R.layout.fragment_shop_dialog) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCancelShopDialog.setOnClickListener {
            dismiss()
        }

        lifecycleScope.launch {
            viewModel.shoppingUiEvent.collectLatest {
                if (it == ShoppingListUiEvent.Tagged) dismiss()
            }
            viewModel.couponDetailUiEvent.collectLatest {
                if (it == CouponDetailUiEvent.Tagged) dismiss()
            }
        }
    }
}
