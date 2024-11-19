package com.ssafy.smartstore_jetpack.presentation.views.main.history

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentHistoryBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(R.layout.fragment_history) {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var orderListAdapter: OrderListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initAdapter()
        initViews()

        collectLatestFlow(viewModel.historyUiEvent) { handleUiEvent(it) }
    }

    private fun initAdapter() {
        orderListAdapter = OrderListAdapter(viewModel)
        binding.adapter = orderListAdapter
    }

    private fun initViews() {
        binding.rvMyPage.scrollToPosition(0)
    }

    private fun handleUiEvent(event: HistoryUiEvent) = when (event) {
        is HistoryUiEvent.GoToOrderDetail -> {
            findNavController().navigateSafely(R.id.action_history_to_order_detail)
        }
    }
}