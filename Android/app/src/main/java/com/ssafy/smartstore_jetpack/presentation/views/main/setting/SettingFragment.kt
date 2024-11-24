package com.ssafy.smartstore_jetpack.presentation.views.main.setting

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentSettingBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        binding.tvVersionValueSetting.text = getAppVersion(requireContext())

        collectLatestFlow(viewModel.settingUiEvent) { handleUiEvent(it) }
    }

    override fun onResume() {
        super.onResume()

        viewModel.setBnvState(false)
    }

    private fun getAppVersion(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionName
            }
            "${packageInfo.versionName} ($versionCode)"
        } catch (e: Exception) {
            "Version info not available"
        }
    }

    private fun handleUiEvent(event: SettingUiEvent) = when (event) {
        is SettingUiEvent.SelectAppTheme -> {
            findNavController().navigateSafely(R.id.action_setting_to_app_theme)
        }

        is SettingUiEvent.DoLogout -> {
            showToastMessage(getString(R.string.message_logout))
            requireActivity().supportFragmentManager.popBackStack()
        }

        else -> {}
    }
}