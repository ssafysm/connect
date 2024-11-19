package com.ssafy.smartstore_jetpack.presentation.views.main.pay

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentPayBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayFragment : BaseFragment<FragmentPayBinding>(R.layout.fragment_pay) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
    }
}