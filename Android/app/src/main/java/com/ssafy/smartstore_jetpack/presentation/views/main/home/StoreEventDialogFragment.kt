package com.ssafy.smartstore_jetpack.presentation.views.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentStoreEventDialogBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseDialogFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel

class StoreEventDialogFragment :
    BaseDialogFragment<FragmentStoreEventDialogBinding>(R.layout.fragment_store_event_dialog) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false

        binding.vm = viewModel

        binding.tvConfirmStoreEvent.setOnClickListener { dismiss() }
    }
}