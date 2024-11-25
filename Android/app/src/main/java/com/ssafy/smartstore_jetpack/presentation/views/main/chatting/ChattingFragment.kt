package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
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
    }

    override fun onResume() {
        super.onResume()

        viewModel.setBnvState(false)
    }

    private fun initRecyclerView() {
        binding.rvChatting.adapter = ChatAdapter(viewModel)
        binding.rvChatting.setHasFixedSize(true)
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
            showToastMessage(getString(R.string.message_need_message_or_image))
        }

        is ChattingUiEvent.SendMessage -> {
            viewModel.sendMessage(requireContext())
        }

        is ChattingUiEvent.GoToMenu -> {
            showToastMessage(getString(R.string.message_preparing_menu))
        }

        is ChattingUiEvent.GoToOrder -> {
            showToastMessage(getString(R.string.message_preparing_menu))
        }

        is ChattingUiEvent.GoToShop -> {
            findNavController().navigateSafely(R.id.action_chatting_to_plan_progress)
        }

        is ChattingUiEvent.GoToPlan -> {
            findNavController().navigateSafely(R.id.action_chatting_to_plan_first)
        }

        else -> {}
    }
}