package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentChattingBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingFragment : BaseFragment<FragmentChattingBinding>(R.layout.fragment_chatting) {

    private val viewModel: MainViewModel by activityViewModels()
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { viewModel.setImageUri(it) }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initRecyclerView()
        setEditText()

        collectLatestFlow(viewModel.chattingUiEvent) { handleUiEvent(it) }

        // 추가적으로 생각해야 할 것
        // 1. 채팅 올라올 때마다 스크롤 내리기
        // 2. 키보드 동작 관리
    }

    override fun onResume() {
        super.onResume()

        viewModel.setBnvState(false)
    }

    private fun initRecyclerView() {
        binding.adapter = ChatAdapter()
        binding.rvChatting.apply {

            addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                if ((binding.adapter as ChatAdapter).itemCount > 0) {
                    scrollToPosition((binding.adapter as ChatAdapter).itemCount - 1)
                }
            }
        }
    }

    private fun setEditText() {
        binding.etChatting.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (binding.btnSubmitChatting.isEnabled) {
                    viewModel.onClickSendChatting()
                }
                true
            } else {
                false
            }
        }
    }

    private fun handleUiEvent(event: ChattingUiEvent) = when (event) {
        is ChattingUiEvent.SelectImage -> {
            pickImageLauncher.launch("image/*")
        }

        is ChattingUiEvent.SendMessageFail -> {
            showToastMessage("메시지나 이미지를 입력하세요.")
        }

        is ChattingUiEvent.SendMessage -> {
            viewModel.sendMessage(requireContext())
        }

        is ChattingUiEvent.GoToMenu -> {
            showToastMessage("준비중인 메뉴에요.")
        }

        is ChattingUiEvent.GoToOrder -> {
            showToastMessage("준비중인 메뉴에요.")
        }

        is ChattingUiEvent.GoToShop -> {
            showToastMessage("준비중인 메뉴에요.")
        }

        is ChattingUiEvent.GoToPlan -> {
            findNavController().navigateSafely(R.id.action_chatting_to_plan_first)
        }

        else -> {}
    }
}