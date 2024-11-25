package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smartstore_jetpack.databinding.FragmentChattingBinding
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingFragment : Fragment() {
    private var _binding: FragmentChattingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChattingViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { viewModel.setImageUri(it) }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChattingBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChattingFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadUserInfo()

        val chatAdapter = ChatAdapter()
        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatAdapter

            // RecyclerView 레이아웃 변경 시 자동 스크롤
            addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                if (chatAdapter.itemCount > 0) {
                    scrollToPosition(chatAdapter.itemCount - 1)
                }
            }
        }

        binding.imageUpload.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.sendButton.setOnClickListener {
            sendMessage()
        }

        binding.chatInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage()
                true
            } else {
                false
            }
        }

        viewModel.chatList.observe(viewLifecycleOwner) { chatList ->
            chatAdapter.submitList(chatList.toList())
            binding.chatRecyclerView.scrollToPosition(chatList.size - 1)
        }

        viewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->
            userInfo?.let {
                binding.welcomeMessage.text = "안녕하세요 ${it.user.name}님. ${it.user.stamps} 스탬프 보유 중!"
            }
        }

        // 키보드 등장 시 RecyclerView 및 입력창 패딩 조정
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val imeInsets = windowInsets.getInsets(WindowInsetsCompat.Type.ime())
            val imeHeight = imeInsets.bottom

            // RecyclerView와 입력창 간 간격 설정
            binding.chatRecyclerView.updatePadding(bottom = imeHeight)
            binding.inputContainer.translationY = -imeHeight.toFloat()
            windowInsets
        }
    }

    private fun sendMessage() {
        val message = binding.chatInput.text.toString()
        if (message.isBlank() && viewModel.imageUri.value == null) {
            Toast.makeText(requireContext(), "메시지나 이미지를 입력하세요.", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.sendMessage(requireContext(), message)
            binding.chatInput.text.clear()
            viewModel.setImageUri(null)
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.setBnvState(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
