package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.annotation.SuppressLint
import android.content.res.Resources
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
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
import timber.log.Timber

@AndroidEntryPoint
class ShoppingListFragment :
    BaseFragment<FragmentShoppingListBinding>(R.layout.fragment_shopping_list) {

    private val viewModel: MainViewModel by activityViewModels()
    private var nfcAdapter: NfcAdapter? = null

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
            val params = binding.glBottomCart.layoutParams as ConstraintLayout.LayoutParams

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

        nfcAdapter = NfcAdapter.getDefaultAdapter(requireContext())

        collectLatestFlow(viewModel.shoppingUiEvent) { handleUiEvent(it) }
    }

    override fun onStart() {
        super.onStart()

        nfcAdapter?.enableReaderMode(
            requireActivity(),
            { tag -> readNfcTag(tag) },
            NfcAdapter.FLAG_READER_NFC_A,
            null
        )
    }

    override fun onResume() {
        super.onResume()

        viewModel.setBnvState(false)
    }

    override fun onStop() {
        super.onStop()

        nfcAdapter?.disableReaderMode(requireActivity())
    }

    private fun initAdapter() {
        binding.rvCart.adapter = ShoppingListAdapter(viewModel)
        binding.rvCart.setHasFixedSize(false)
    }

    private fun readNfcTag(tag: Tag) {
        val ndef = Ndef.get(tag)
        ndef?.let {
            try {
                it.connect()
                val ndefMessage = it.cachedNdefMessage
                val record = ndefMessage.records[0]
                val payload = record.payload
                val textData = String(payload, 3, payload.size - 3) // 첫 3바이트는 메타데이터

                requireActivity().runOnUiThread {
                    Timber.d("NFC Text: $textData")
                    val tableNumber = textData.split(":")[0].toInt()
                    viewModel.setTableNumber(tableNumber)
                    Toast.makeText(
                        requireContext(),
                        "${tableNumber}번 테이블 번호가 등록 되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                it.close()
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "NFC 읽기 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
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