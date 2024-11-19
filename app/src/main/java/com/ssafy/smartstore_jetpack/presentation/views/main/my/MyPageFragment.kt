package com.ssafy.smartstore_jetpack.presentation.views.main.my

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentMypageBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import com.ssafy.smartstore_jetpack.presentation.views.main.orderdetail.OrderListAdapter
import com.ssafy.smartstore_jetpack.presentation.views.signin.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var orderListAdapter: OrderListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initAdapter()
        initViews()

        collectLatestFlow(viewModel.myPageUiEvent) { handleUiEvent(it) }
    }

    override fun onResume() {
        super.onResume()

        initViews()
        viewModel.setBnvState(true)
    }

    private fun initAdapter() {
        orderListAdapter = OrderListAdapter(viewModel)
        binding.adapter = orderListAdapter
    }

    private fun initViews() {
        viewModel.user.value?.let {
            binding.pbMyPage.max = it.grade.stepMax.toInt()
            binding.pbMyPage.progress = (it.grade.stepMax.toInt() - it.grade.to.toInt())
            binding.pbMyPage.invalidate()
            binding.tvRemainMyPage.text =
                (it.grade.stepMax.toInt() - it.grade.to.toInt()).toString()
        }
        when (viewModel.orders6Months.value.isNotEmpty()) {
            true -> {
                binding.ivEmptyMyPage.visibility = View.GONE
                binding.rvMyPage.visibility = View.VISIBLE
            }

            else -> {
                binding.ivEmptyMyPage.visibility = View.VISIBLE
                binding.rvMyPage.visibility = View.GONE
            }
        }
        binding.rvMyPage.scrollToPosition(0)
    }

    private fun handleUiEvent(event: MyPageUiEvent) = when (event) {
        is MyPageUiEvent.GoToSettings -> {
            findNavController().navigateSafely(R.id.action_my_page_to_setting)
        }

        is MyPageUiEvent.GoToOrderDetail -> {
            findNavController().navigateSafely(R.id.action_my_page_to_order_detail)
        }

        is MyPageUiEvent.DoLogout -> {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}