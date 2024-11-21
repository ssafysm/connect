package com.ssafy.smartstore_jetpack.presentation.views.main.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentAppThemeBottomSheetBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseBottomSheetDialogFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainActivity
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppThemeBottomSheetFragment :
    BaseBottomSheetDialogFragment<FragmentAppThemeBottomSheetBinding>(R.layout.fragment_app_theme_bottom_sheet) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        lifecycleScope.launch {
            viewModel.settingUiEvent.collectLatest { uiEvent ->
                when (uiEvent) {
                    is SettingUiEvent.SubmitAppTheme -> {
                        (requireActivity() as MainActivity).applyTheme(uiEvent.theme)
                        dismiss()
                    }

                    is SettingUiEvent.CloseAppTheme -> dismiss()

                    else -> {}
                }
            }
        }
    }
}