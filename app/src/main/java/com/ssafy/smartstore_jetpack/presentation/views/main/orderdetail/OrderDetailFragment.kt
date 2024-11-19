package com.ssafy.smartstore_jetpack.presentation.views.main.orderdetail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.databinding.FragmentOrderDetailBinding
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.dateFormat
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.makeCommaWon
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class OrderDetailFragment :
    BaseFragment<FragmentOrderDetailBinding>(R.layout.fragment_order_detail) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initRecyclerView()
        initViews()
    }

    override fun onResume() {
        super.onResume()

        initViews()
        viewModel.setBnvState(false)
    }

    private fun initRecyclerView() {
        binding.adapter = OrderDetailListAdapter()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        viewModel.selectedOrder.value?.let { order ->
            binding.tvTotalPriceOrderDetail.text =
                makeCommaWon(order.details.sumOf { it.quantity * it.unitPrice.toInt() })
            binding.tvDateOrderDetail.text = dateFormat(order.orderTime)
        }
    }
}