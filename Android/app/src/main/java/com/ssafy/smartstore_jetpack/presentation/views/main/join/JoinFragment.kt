package com.ssafy.smartstore_jetpack.presentation.views.main.join

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentJoinBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        collectLatestFlow(viewModel.joinUiEvent) { handleUiEvent(it) }
    }

    override fun onResume() {
        super.onResume()

        viewModel.setBnvState(false)
    }

    private fun handleUiEvent(event: JoinUiEvent) = when (event) {
        is JoinUiEvent.CheckId -> {
            when (event.isUsedId) {
                false -> {
                    Toast.makeText(requireContext(), "사용 가능한 ID라네요!", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(requireContext(), "이미 사용중인 ID거나, 다른 오류가 있습니다!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        is JoinUiEvent.JoinFail -> {
            Toast.makeText(requireContext(), "회원 가입에 실패했습니다!", Toast.LENGTH_SHORT).show()
        }

        is JoinUiEvent.GoToLogin -> {
            findNavController().navigate(
                R.id.fragment_login,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.fragment_join, true)
                    .build())
            // findNavController().navigateSafely(R.id.action_join_to_login)
        }
    }
}