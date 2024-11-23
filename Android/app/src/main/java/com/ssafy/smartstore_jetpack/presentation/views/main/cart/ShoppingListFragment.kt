package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentShoppingListBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.util.BlurHelper.applyBlur
import com.ssafy.smartstore_jetpack.presentation.util.BlurHelper.clearBlur
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoppingListFragment :
    BaseFragment<FragmentShoppingListBinding>(R.layout.fragment_shopping_list) {

    private val viewModel: MainViewModel by activityViewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initAdapter()

        binding.clBottomCart.post {
            val layoutHeight = binding.clBottomCart.height
            val displayMetrics = Resources.getSystem().displayMetrics
            val screenHeight = displayMetrics.heightPixels
            val heightPercent = (screenHeight - layoutHeight).toFloat() / screenHeight * 100

            binding.glBottomCart.setGuidelinePercent(heightPercent - 10F)

            binding.rvCart.setOnTouchListener { _, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> binding.rvCart.parent.requestDisallowInterceptTouchEvent(
                        true
                    )

                    MotionEvent.ACTION_UP -> binding.rvCart.parent.requestDisallowInterceptTouchEvent(
                        false
                    )
                }
                false
            }

            lifecycleScope.launch {
                viewModel.shoppingList.collectLatest {
                    if (it.size > 2) {
                        binding.rvCart.addOnScrollListener(object :
                            RecyclerView.OnScrollListener() {
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                super.onScrolled(recyclerView, dx, dy)
                                if (dy > 0) {
                                    hideBottomLayout(binding.clBottomCart)
                                } else if (dy < 0) {
                                    showBottomLayout(binding.clBottomCart)
                                }
                            }
                        })
                    } else if (it.isNotEmpty()) {
                        showBottomLayout(binding.clBottomCart)
                    }
                }
            }
        }

        collectLatestFlow(viewModel.shoppingUiEvent) { handleUiEvent(it) }
    }

    override fun onResume() {
        super.onResume()

        viewModel.setBnvState(false)
    }

    private fun initAdapter() {
        binding.rvCart.adapter = ShoppingListAdapter(viewModel)
        binding.rvCart.setHasFixedSize(false)
    }

    private fun handleUiEvent(event: ShoppingListUiEvent) = when (event) {
        is ShoppingListUiEvent.ShopOrder -> {
            findNavController().navigateSafely(R.id.cart_to_shop_select)
            applyBlur(binding.fragmentShoppingList, 20F)
        }

        is ShoppingListUiEvent.TakeOutOrder -> {
            findNavController().navigateSafely(R.id.cart_to_shop_select)
            applyBlur(binding.fragmentShoppingList, 20F)
        }

        is ShoppingListUiEvent.FinishOrder -> {
            Toast.makeText(
                requireContext(),
                "주문에 성공했어요! 주문 번호는 ${event.orderId}번이에요.",
                Toast.LENGTH_SHORT
            ).show()
            requireActivity().supportFragmentManager.popBackStack()
            clearBlur(binding.fragmentShoppingList)
        }

        is ShoppingListUiEvent.OrderFail -> {
            Toast.makeText(requireContext(), "주문에 실패했어요ㅠㅠ", Toast.LENGTH_SHORT).show()
            clearBlur(binding.fragmentShoppingList)
        }

        else -> {}
    }
}