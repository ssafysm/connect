package com.ssafy.smartstore_jetpack.presentation.views.main.my

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
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
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
        is MyPageUiEvent.GoToJoin -> {
            findNavController().navigateSafely(R.id.action_my_page_to_join)
        }

        is MyPageUiEvent.GoToLogin -> {
            findNavController().navigateSafely(R.id.action_my_page_to_login)
        }

        is MyPageUiEvent.GoToSettings -> {
            findNavController().navigateSafely(R.id.action_my_page_to_setting)
        }

        is MyPageUiEvent.GoToHistory -> {
            when (viewModel.user.value) {
                null -> showToastMessage(getString(R.string.message_need_to_login))

                else -> findNavController().navigateSafely(R.id.action_my_page_to_history)
            }
        }

        is MyPageUiEvent.GoToInformation -> {
            when (viewModel.user.value) {
                null -> showToastMessage(getString(R.string.message_need_to_login))

                else -> findNavController().navigateSafely(R.id.action_my_page_to_information)
            }
        }

        is MyPageUiEvent.GoToCoupon -> {
            when (viewModel.user.value) {
                null -> showToastMessage(getString(R.string.message_need_to_login))

                else -> findNavController().navigateSafely(R.id.action_my_page_to_coupon)
            }
        }

        is MyPageUiEvent.GoToPay -> {
            when (viewModel.user.value) {
                null -> showToastMessage(getString(R.string.message_need_to_login))

                else -> findNavController().navigateSafely(R.id.action_my_page_to_attendance)
            }
        }
    }
}