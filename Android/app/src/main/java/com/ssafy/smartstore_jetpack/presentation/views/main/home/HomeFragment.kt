package com.ssafy.smartstore_jetpack.presentation.views.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentHomeBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainActivity
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: MainViewModel by activityViewModels()
    private var pageChangeCallback: ViewPager2.OnPageChangeCallback? = null
    private var autoScrollJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initRecyclerView()

        collectLatestFlow(viewModel.homeUiEvent) { handleUiEvent(it) }
    }

    override fun onResume() {
        super.onResume()

        viewModel.setBnvState(true)
        binding.rvLatestOrderHome.scrollToPosition(0)

        binding.vpTodayEventHome.post {
            binding.vpTodayEventHome.setCurrentItem(viewModel.nowEventIndex.value, false)
        }
        startAutoScroll(binding.vpTodayEventHome)
        pageChangeCallback?.let { binding.vpTodayEventHome.registerOnPageChangeCallback(it) }
    }

    override fun onPause() {
        super.onPause()

        pageChangeCallback?.let { binding.vpTodayEventHome.unregisterOnPageChangeCallback(it) }
        pageChangeCallback = null
        stopAutoScroll()
        viewModel.setEventsIndex(binding.vpTodayEventHome.currentItem)
    }

    private fun initRecyclerView() {
        binding.orderAdapter = OrderAdapter(viewModel)
        binding.vpTodayEventHome.adapter = EventAdapter()
        binding.vpTodayEventHome.offscreenPageLimit = 5
        binding.vpTodayEventHome.setPageTransformer { page, position ->
            val scaleFactor = 0.85F + (1 - abs(position)) * 0.15F
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
            page.alpha = 0.8F + (1 - abs(position)) * 0.2F
        }
        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    stopAutoScroll()
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    startAutoScroll(binding.vpTodayEventHome)
                }
            }
        }
    }

    private fun startAutoScroll(viewPager: ViewPager2, interval: Long = 5000L) {
        if (autoScrollJob?.isActive == true) {
            return
        }

        autoScrollJob?.cancel()

        autoScrollJob = lifecycleScope.launch {
            while (isActive) {
                delay(interval)
                val currentItem = viewPager.currentItem

                val itemCount = when (viewPager.adapter) {
                    null -> 1

                    else -> viewPager.adapter!!.itemCount
                }

                when (currentItem) {
                    itemCount - 2 -> viewPager.setCurrentItem(1, true)

                    else -> viewPager.currentItem = (currentItem + 1) % itemCount
                }
            }
        }
    }

    private fun stopAutoScroll() {
        autoScrollJob?.cancel()
        autoScrollJob = null
    }

    private fun handleUiEvent(event: HomeUiEvent) = when (event) {
        is HomeUiEvent.GoToJoin -> {
            findNavController().navigateSafely(R.id.action_home_to_join)
        }

        is HomeUiEvent.GoToLogin -> {
            findNavController().navigateSafely(R.id.action_home_to_login)
        }

        is HomeUiEvent.GoToNotice -> {
            findNavController().navigateSafely(R.id.action_home_to_notice)
        }

        is HomeUiEvent.GoToOrderDetail -> {
            findNavController().navigateSafely(R.id.action_home_to_order_detail)
        }

        is HomeUiEvent.GoToChatting -> {
            findNavController().navigateSafely(R.id.action_home_to_chatting)
        }

        is HomeUiEvent.GetUserInfo -> {
            (requireActivity() as MainActivity).initFCM()
        }
    }
}