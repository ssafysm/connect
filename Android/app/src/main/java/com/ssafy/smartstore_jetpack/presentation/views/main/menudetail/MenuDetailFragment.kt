package com.ssafy.smartstore_jetpack.presentation.views.main.menudetail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialContainerTransform
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.databinding.FragmentMenuDetailBinding
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

@SuppressLint("ClickableViewAccessibility")
@AndroidEntryPoint
class MenuDetailFragment : BaseFragment<FragmentMenuDetailBinding>(R.layout.fragment_menu_detail){

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300
            scrimColor = Color.TRANSPARENT
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            duration = 300
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        viewModel.setBnvState(false)
        initRecyclerView()
        initViews()
        setEditTextFocus()

        collectLatestFlow(viewModel.menuDetailUiEvent) { handleUiEvent(it) }
    }

    override fun onResume() {
        super.onResume()

        initViews()
    }

    private fun initRecyclerView() {
        binding.adapter = CommentAdapter(viewModel, viewModel.user.value?.user?.id ?: "")
    }

    private fun initViews() {
        binding.nsvMenuDetail.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY && binding.clBottomMenuDetail.visibility == View.VISIBLE) {
                hideBottomLayout(binding.clBottomMenuDetail)
            } else if (scrollY < oldScrollY && binding.clBottomMenuDetail.visibility == View.GONE) {
                showBottomLayout(binding.clBottomMenuDetail)
            }
        }

        lifecycleScope.launch {
            viewModel.selectedProduct.collectLatest { product ->
                if ((product != null) && (product.productRatingAvg != null)) {
                    binding.ablMenuDetail.addOnOffsetChangedListener { _, verticalOffset ->
                        val totalScrollRange = binding.ablMenuDetail.totalScrollRange
                        val collapseRatio = abs(verticalOffset).toFloat() / totalScrollRange

                        val alpha = (collapseRatio * 255).toInt()

                        when (viewModel.appThemeName.value) {
                            "봄" -> binding.toolbarDetail.setBackgroundColor(
                                Color.argb(
                                    alpha,
                                    255,
                                    231,
                                    233
                                )
                            )

                            "여름" -> binding.toolbarDetail.setBackgroundColor(
                                Color.argb(
                                    alpha,
                                    223,
                                    241,
                                    255
                                )
                            )

                            "가을" -> binding.toolbarDetail.setBackgroundColor(
                                Color.argb(
                                    alpha,
                                    254,
                                    234,
                                    205
                                )
                            )

                            "겨울" -> binding.toolbarDetail.setBackgroundColor(
                                Color.argb(
                                    alpha,
                                    218,
                                    250,
                                    247
                                )
                            )

                            else -> binding.toolbarDetail.setBackgroundColor(
                                Color.argb(
                                    alpha,
                                    245,
                                    245,
                                    220
                                )
                            )
                        }
                        binding.toolbarDetail.elevation = 10F

                        binding.ctlMenuDetail.title = if (collapseRatio > 0.7f) product.name else ""
                    }
                }
            }
        }
        binding.ivTitleDetail.transitionName = "menu_detail_${viewModel.selectedProduct.value?.id}"
    }

    private fun setEditTextFocus() {
        with(binding) {
            etCommentDetail.setOnEditorActionListener { _, actionId, _ ->
                when (actionId == EditorInfo.IME_ACTION_DONE) {
                    true -> {
                        if (btnEnrollDetail.isEnabled) {
                            viewModel.onClickSelectRating()
                        }
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun handleUiEvent(event: MenuDetailUiEvent) = when (event) {
        is MenuDetailUiEvent.SubmitComment ->{
            binding.rvDetail.smoothScrollToPosition(0)
        }

        is MenuDetailUiEvent.SelectRating -> {
            findNavController().navigateSafely(R.id.detail_to_rating_dialog)
        }

        is MenuDetailUiEvent.SelectProduct -> {
            requireActivity().supportFragmentManager.popBackStack()
        }

        is MenuDetailUiEvent.UpdateComment -> {
            Toast.makeText(requireContext(), "댓글을 수정했어용!", Toast.LENGTH_SHORT).show()
        }

        is MenuDetailUiEvent.UpdateCommentFail -> {
            Toast.makeText(requireContext(), "댓글 수정에 실패했어용...", Toast.LENGTH_SHORT).show()
        }

        is MenuDetailUiEvent.DeleteComment -> {
            Toast.makeText(requireContext(), "댓글을 삭제했어용!", Toast.LENGTH_SHORT).show()
        }

        is MenuDetailUiEvent.DeleteCommentFail -> {
            Toast.makeText(requireContext(), "댓글 삭제에 실패했어용...", Toast.LENGTH_SHORT).show()
        }
    }
}