package com.ssafy.smartstore_jetpack.presentation.views.main.information

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentInformationBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationFragment : BaseFragment<FragmentInformationBinding>(R.layout.fragment_information) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        collectLatestFlow(viewModel.informationUiEvent) { handleUiEvent(it) }
    }

    private fun handleUiEvent(event: InformationUiEvent) = when (event) {
        is InformationUiEvent.GoToPassword -> {
            findNavController().navigateSafely(R.id.action_information_to_password)
        }
    }
}