package com.ssafy.smartstore_jetpack.presentation.views.main.order

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialContainerTransform
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.databinding.FragmentOrderBinding
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>(R.layout.fragment_order) {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var menuPageAdapter: MenuPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300
        }

        sharedElementReturnTransition = MaterialContainerTransform().apply {
            duration = 300
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initViewPager()

        collectLatestFlow(viewModel.orderUiEvent) { handleUiEvent(it) }
    }

    override fun onStart() {
        super.onStart()

        viewModel.setFabState(true)
        viewModel.setBnvState(true)
    }

    override fun onStop() {
        super.onStop()

        viewModel.setFabState(false)
    }

    private fun initViewPager() {
        menuPageAdapter = MenuPageAdapter(requireActivity())
        with(binding.vpOrder) {
            adapter = menuPageAdapter
            setCurrentItem(MenuPageAdapter.START_POSITION, true)
        }
        initTabLayout()
    }

    private fun initTabLayout() {
        TabLayoutMediator(
            binding.tlOrder,
            binding.vpOrder
        ) { tab, position ->
            tab.text = resources.getStringArray(R.array.menutype)[position]
        }.attach()
        for (i in 0 until resources.getStringArray(R.array.menutype).size) {
            val tabs = binding.tlOrder.getChildAt(0) as ViewGroup
            for (tab in tabs.children) {
                val lp = tab.layoutParams as LinearLayout.LayoutParams
                lp.marginEnd = 16
                tab.layoutParams = lp
                binding.tlOrder.requestLayout()
            }
        }
    }

    private fun handleUiEvent(event: OrderUiEvent) = when (event) {
        is OrderUiEvent.GoToCart -> {
            findNavController().navigateSafely(R.id.action_order_to_cart)
        }

        else -> {}
    }
}