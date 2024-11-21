package com.ssafy.smartstore_jetpack.presentation.views.main.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.databinding.FragmentLoginBinding
import com.ssafy.smartstore_jetpack.presentation.views.main.MainActivity
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login){

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        collectLatestFlow(viewModel.loginUiEvent) { handleUiEvent(it) }
    }

    override fun onResume() {
        super.onResume()

        viewModel.setBnvState(false)
    }

    private fun handleUiEvent(event: LoginUiEvent) = when (event) {
        is LoginUiEvent.GoToLogin -> {
            requireActivity().supportFragmentManager.popBackStack()
        }

        is LoginUiEvent.LoginFail -> {
            Toast.makeText(requireContext(), "로그인에 실패했어요ㅠㅠ", Toast.LENGTH_SHORT).show()
        }

        is LoginUiEvent.GoToJoin -> {
            findNavController().navigateSafely(R.id.action_login_to_join)
        }
    }
}