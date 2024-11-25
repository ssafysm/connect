package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentChattingPlanFirstBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseDialogFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChattingPlanFirstFragment :
    BaseDialogFragment<FragmentChattingPlanFirstBinding>(R.layout.fragment_chatting_plan_first) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        binding.ivChattingPlanFirst.setOnClickListener { dismiss() }

        lifecycleScope.launch {
            viewModel.chattingUiEvent.collectLatest { uiEvent ->
                if (uiEvent == ChattingUiEvent.GoToPlan2) {
                    findNavController().navigateSafely(
                        R.id.action_plan_first_to_second,
                        null,
                        NavOptions.Builder().setPopUpTo(R.id.fragment_chatting_plan_first, true)
                            .build()
                    )
                }
            }
        }
    }
}