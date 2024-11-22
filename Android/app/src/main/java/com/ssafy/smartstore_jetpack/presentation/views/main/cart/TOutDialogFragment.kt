package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentTOutDialogBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseDialogFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel

class TOutDialogFragment :
    BaseDialogFragment<FragmentTOutDialogBinding>(R.layout.fragment_t_out_dialog) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCancelTOutDialog.setOnClickListener {
            dismiss()
        }
        binding.tvConfirmTOutDialog.setOnClickListener {
            dismiss()
            viewModel.onClickShoppingFinish()
        }
    }
}
