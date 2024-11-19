package com.ssafy.smartstore_jetpack.presentation.views.main.menudetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentRatingDialogBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseDialogFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RatingDialogFragment : BaseDialogFragment<FragmentRatingDialogBinding>(R.layout.fragment_rating_dialog) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        lifecycleScope.launch {
            viewModel.menuDetailUiEvent.collectLatest {
                when (it) {
                    is MenuDetailUiEvent.SubmitComment -> dismiss()

                    else -> {}
                }
            }
        }
    }
}
