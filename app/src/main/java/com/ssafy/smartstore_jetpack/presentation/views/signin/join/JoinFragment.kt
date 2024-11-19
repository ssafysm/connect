package com.ssafy.smartstore_jetpack.presentation.views.signin.join

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentJoinBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.views.signin.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        collectLatestFlow(viewModel.joinUiEvent) { handleUiEvent(it) }
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
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}