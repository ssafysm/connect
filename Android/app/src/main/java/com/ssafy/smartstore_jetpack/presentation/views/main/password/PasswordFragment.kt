package com.ssafy.smartstore_jetpack.presentation.views.main.password

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentPasswordBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordFragment : BaseFragment<FragmentPasswordBinding>(R.layout.fragment_password) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        collectLatestFlow(viewModel.passwordUiEvent) { handleUiEvent(it) }
    }

    private fun handleUiEvent(event: PasswordUiEvent) = when (event) {
        is PasswordUiEvent.PasswordUpdateSuccess -> {
            findNavController().popBackStack(R.id.fragment_my_page, false)
            Toast.makeText(requireContext(), "비밀번호를 성공적으로 변경했어요.", Toast.LENGTH_SHORT).show()
        }

        is PasswordUiEvent.PasswordUpdateFailed -> {
            Toast.makeText(requireContext(), "비밀번호 변경에 실패했어요...", Toast.LENGTH_SHORT).show()
        }
    }
}