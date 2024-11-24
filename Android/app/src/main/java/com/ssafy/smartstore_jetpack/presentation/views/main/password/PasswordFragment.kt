package com.ssafy.smartstore_jetpack.presentation.views.main.password

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
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

        setEditTextFocus()

        collectLatestFlow(viewModel.passwordUiEvent) { handleUiEvent(it) }
    }

    private fun setEditTextFocus() {
        with(binding) {
            etPassword.setOnEditorActionListener { _, actionId, _ ->
                when (actionId == EditorInfo.IME_ACTION_NEXT) {
                    true -> {
                        etConfirmPassword.requestFocus()
                        true
                    }

                    else -> false
                }
            }
            etConfirmPassword.setOnEditorActionListener { _, actionId, _ ->
                when (actionId == EditorInfo.IME_ACTION_DONE) {
                    true -> {
                        if (btnPassword.isEnabled) {
                            viewModel.onClickPasswordUpdate()
                        }
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun handleUiEvent(event: PasswordUiEvent) = when (event) {
        is PasswordUiEvent.PasswordUpdateSuccess -> {
            findNavController().popBackStack(R.id.fragment_my_page, false)
            showToastMessage(getString(R.string.message_password_update))
        }

        is PasswordUiEvent.PasswordUpdateFailed -> {
            showToastMessage(getString(R.string.message_password_update_fail))
        }
    }
}