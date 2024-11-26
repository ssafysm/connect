package com.ssafy.smartstore_jetpack.presentation.views.main.notice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentNoticeBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeFragment : BaseFragment<FragmentNoticeBinding>(R.layout.fragment_notice) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initRecyclerView()

        collectLatestFlow(viewModel.noticeUiEvent) { handleUiEvent(it) }
    }

    override fun onResume() {
        super.onResume()

        viewModel.setBnvState(false)
    }

    private fun initRecyclerView() {
        binding.adapter = NoticeAdapter(viewModel)
    }

    private fun handleUiEvent(event: NoticeUiEvent) = when (event) {
        is NoticeUiEvent.DeleteNotice -> {
            showToastMessage(getString(R.string.message_notice_delete))
        }
    }
}



