package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentPlanProgressBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseDialogFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlanProgressFragment :
    BaseDialogFragment<FragmentPlanProgressBinding>(R.layout.fragment_plan_progress) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        setEditTextFocus()

        lifecycleScope.launch {
            viewModel.chattingUiEvent.collectLatest { uiEvent ->
                when (uiEvent) {
                    is ChattingUiEvent.SubmitPlanProgress -> {
                        dismiss()
                    }

                    is ChattingUiEvent.SubmitPlanFinish -> {
                        showToastMessage(getString(R.string.message_plan_progress_submit))
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setEditTextFocus() {
        with(binding) {
            etChattingPlanProgress.requestFocus()
            showKeyboard(etChattingPlanProgress)
            etChattingPlanProgress.setOnEditorActionListener { _, actionId, _ ->
                when (actionId == EditorInfo.IME_ACTION_SEND) {
                    true -> {
                        if (btnSubmitChattingPlanProgress.isEnabled) {
                            viewModel.onClickPlanProgressSubmitChatting()
                        }
                        true
                    }

                    else -> false
                }
            }
        }
    }
}