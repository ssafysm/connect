package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentChattingPlanSecondBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseDialogFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChattingPlanSecondFragment :
    BaseDialogFragment<FragmentChattingPlanSecondBinding>(R.layout.fragment_chatting_plan_second) {

    private val viewModel: MainViewModel by activityViewModels()
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { viewModel.setImagePlanUri(it) }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        binding.ivChattingPlanSecond.setOnClickListener { dismiss() }

        lifecycleScope.launch {
            viewModel.chattingUiEvent.collectLatest { uiEvent ->
                when (uiEvent) {
                    is ChattingUiEvent.GoToPlanImage -> {
                        pickImageLauncher.launch("image/*")
                        // 서버 통신 로직 추가
                    }

                    is ChattingUiEvent.SubmitPlanFinish -> {
                        showToastMessage("플랜 전송을 완료했어요.")
                        dismiss()
                    }

                    else -> {}
                }
            }
        }
    }
}