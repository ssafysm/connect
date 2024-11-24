package com.ssafy.smartstore_jetpack.presentation.views.main.join

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
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

        initView()
        setEditTextFocus()

        collectLatestFlow(viewModel.joinUiEvent) { handleUiEvent(it) }
    }

    override fun onResume() {
        super.onResume()

        viewModel.setBnvState(false)
    }

    private fun initView() {
        with(binding) {
            tilIdJoin.setEndIconOnClickListener {
                val inputText = etIdJoin.text.toString()
                if (inputText.isNotEmpty()) {
                    viewModel.onClickCheckId()
                }
            }
        }
    }

    private fun setEditTextFocus() {
        with(binding) {
            etIdJoin.requestFocus()
            showKeyboard(etIdJoin)
            etIdJoin.setOnEditorActionListener { _, actionId, _ ->
                when (actionId == EditorInfo.IME_ACTION_NEXT) {
                    true -> {
                        if (btnCheckJoin.isEnabled) {
                            viewModel.onClickCheckId()
                        }
                        true
                    }

                    else -> false
                }
            }
            etPwJoin.setOnEditorActionListener { _, actionId, _ ->
                when (actionId == EditorInfo.IME_ACTION_NEXT) {
                    true -> {
                        etPwConfirmJoin.requestFocus()
                        true
                    }

                    else -> false
                }
            }
            etPwConfirmJoin.setOnEditorActionListener { _, actionId, _ ->
                when (actionId == EditorInfo.IME_ACTION_NEXT) {
                    true -> {
                        etNicknameJoin.requestFocus()
                        true
                    }

                    else -> false
                }
            }
            etNicknameJoin.setOnEditorActionListener { _, actionId, _ ->
                when (actionId == EditorInfo.IME_ACTION_DONE) {
                    true -> {
                        if (btnJoin.isEnabled) {
                            viewModel.onClickGoToJoin()
                        }
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun handleUiEvent(event: JoinUiEvent) = when (event) {
        is JoinUiEvent.CheckId -> {
            when (event.isUsedId) {
                false -> {
                    binding.etPwJoin.requestFocus()
                    showKeyboard(binding.etPwJoin)
                    showToastMessage(getString(R.string.message_join_id_duplicate))
                }

                else -> {
                    binding.etIdJoin.requestFocus()
                    showKeyboard(binding.etIdJoin)
                    showToastMessage(getString(R.string.message_join_id_able))
                }
            }
        }

        is JoinUiEvent.JoinFail -> {
            showToastMessage(getString(R.string.message_join_fail))
        }

        is JoinUiEvent.GoToLogin -> {
            findNavController().navigate(
                R.id.fragment_login,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.fragment_join, true)
                    .build())
        }
    }
}