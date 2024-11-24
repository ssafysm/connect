package com.ssafy.smartstore_jetpack.presentation.views.main.menudetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
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

@SuppressLint("ClickableViewAccessibility")
@AndroidEntryPoint
class RatingDialogFragment : BaseDialogFragment<FragmentRatingDialogBinding>(R.layout.fragment_rating_dialog) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initViews()

        lifecycleScope.launch {
            viewModel.menuDetailUiEvent.collectLatest {
                when (it) {
                    is MenuDetailUiEvent.SubmitComment -> dismiss()

                    else -> {}
                }
            }
        }
    }

    private fun initViews() {
        binding.pbRatingDialog.setOnTouchListener { _, event ->
            val x = event.x
            val totalWidth = binding.pbRatingDialog.width
            val calculatedRating = ((x / totalWidth) * binding.pbRatingDialog.max).toInt().coerceIn(0, binding.pbRatingDialog.max)

            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    binding.tvCurrentRating.visibility = View.VISIBLE

                    val progressBarLeft = binding.pbRatingDialog.left
                    val progressX = progressBarLeft + (binding.pbRatingDialog.width * (calculatedRating.toFloat() / binding.pbRatingDialog.max)).toInt()
                    binding.tvCurrentRating.x =
                        (progressX - binding.tvCurrentRating.width / 2).toFloat()

                    binding.pbRatingDialog.progress = calculatedRating
                    val rating = calculatedRating.toFloat() / 20F
                    binding.tvCurrentRating.text = rating.toString()
                    viewModel.setRating(rating)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    binding.tvCurrentRating.visibility = View.GONE
                }
            }
            true
        }
    }
}
