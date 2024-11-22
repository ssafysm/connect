package com.ssafy.smartstore_jetpack.presentation.views.main.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.databinding.FragmentLoginBinding
import com.ssafy.smartstore_jetpack.presentation.views.main.MainActivity
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        setEditTextFocus()

        collectLatestFlow(viewModel.loginUiEvent) { handleUiEvent(it) }
    }

    override fun onResume() {
        super.onResume()

        viewModel.setBnvState(false)
    }

    private fun setEditTextFocus() {
        with(binding) {
            etIdLogin.setOnEditorActionListener { _, actionId, _ ->
                when (actionId == EditorInfo.IME_ACTION_NEXT) {
                    true -> {
                        etPwLogin.requestFocus()
                        true
                    }

                    else -> false
                }
            }
            etPwLogin.setOnEditorActionListener { _, actionId, _ ->
                when (actionId == EditorInfo.IME_ACTION_DONE) {
                    true -> {
                        if (btnLoginLogin.isEnabled) {
                            viewModel.onClickLogin()
                        }
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun handleUiEvent(event: LoginUiEvent) = when (event) {
        is LoginUiEvent.GoToLogin -> {
            findNavController().navigate(
                R.id.fragment_home,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.fragment_home, true)
                    .build())
            Toast.makeText(
                requireContext(),
                getString(R.string.message_login_success),
                Toast.LENGTH_SHORT
            ).show()
        }

        is LoginUiEvent.LoginFail -> {
            Toast.makeText(
                requireContext(),
                getString(R.string.message_login_failed),
                Toast.LENGTH_SHORT
            ).show()
        }

        is LoginUiEvent.GoToJoin -> {
            findNavController().navigateSafely(R.id.action_login_to_join)
        }

        is LoginUiEvent.GetUserInfo -> {
            (requireActivity() as MainActivity).initFCM()
        }
    }
}