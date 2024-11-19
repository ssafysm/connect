package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentShopSelectBottomSheetDialogBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseBottomSheetDialogFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShopSelectBottomSheetDialogFragment :
    BaseBottomSheetDialogFragment<FragmentShopSelectBottomSheetDialogBinding>(R.layout.fragment_shop_select_bottom_sheet_dialog) {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var shopItemAdapter: ShopItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initRecyclerView()

        lifecycleScope.launch {
            viewModel.shoppingUiEvent.collectLatest { uiEvent ->
                when (uiEvent) {
                    is ShoppingListUiEvent.OrderFail -> dismiss()

                    else -> {}
                }
            }
        }

//        binding.tvConfirmTOutDialog.setOnClickListener {
//            dismiss()
//            viewModel.onClickTakeOutFinish()
//        }
    }

    private fun initRecyclerView() {
        shopItemAdapter = ShopItemAdapter(viewModel)
        binding.adapter = shopItemAdapter
    }
}