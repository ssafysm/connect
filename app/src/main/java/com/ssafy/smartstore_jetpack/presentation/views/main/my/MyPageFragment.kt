package com.ssafy.smartstore_jetpack.presentation.views.main.my

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentMypageBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import com.ssafy.smartstore_jetpack.presentation.views.signin.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        collectLatestFlow(viewModel.myPageUiEvent) { handleUiEvent(it) }
    }

    override fun onResume() {
        super.onResume()

        viewModel.setBnvState(true)
    }

    private fun handleUiEvent(event: MyPageUiEvent) = when (event) {
        is MyPageUiEvent.GoToSettings -> {
            findNavController().navigateSafely(R.id.action_my_page_to_setting)
        }

        is MyPageUiEvent.GoToHistory -> {
            findNavController().navigateSafely(R.id.action_my_page_to_history)
        }

        is MyPageUiEvent.GoToInformation -> {
            findNavController().navigateSafely(R.id.action_my_page_to_information)
        }

        is MyPageUiEvent.GoToCoupon -> {
            findNavController().navigateSafely(R.id.action_my_page_to_coupon)
        }

        is MyPageUiEvent.GoToPay -> {
            findNavController().navigateSafely(R.id.action_my_page_to_pay)
        }

        is MyPageUiEvent.DoLogout -> {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}