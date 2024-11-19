package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentShoppingListBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.util.BlurHelper.applyBlur
import com.ssafy.smartstore_jetpack.presentation.util.BlurHelper.clearBlur
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingListFragment :
    BaseFragment<FragmentShoppingListBinding>(R.layout.fragment_shopping_list) {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var shoppingListAdapter: ShoppingListAdapter
    private var nfcAdapter: NfcAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        initAdapter()
        // initEvent()
        // refreshList()

        // NFC 초기화
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
        shoppingListAdapter = ShoppingListAdapter(viewModel)
        binding.adapter = shoppingListAdapter
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
                    val tableNumber = textData.split(":")[1].toInt()
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

        }

        is ShoppingListUiEvent.TakeOutOrder -> {
            findNavController().navigateSafely(R.id.cart_to_shop_select)
            applyBlur(binding.fragmentShoppingList, 20F)
        }

        is ShoppingListUiEvent.FinishOrder -> {
            requireActivity().supportFragmentManager.popBackStack()
            clearBlur(binding.fragmentShoppingList)
        }

        is ShoppingListUiEvent.OrderFail -> {
            Toast.makeText(requireContext(), "주문에 실패했어요ㅠㅠ", Toast.LENGTH_SHORT).show()
            clearBlur(binding.fragmentShoppingList)
        }

        else -> {}
    }

//    private fun refreshList() {
//        shoppingListAdapter.list = activityViewModel.shoppingList
//        shoppingListAdapter.notifyDataSetChanged()
//    }
//
//    private fun initEvent() {
//        binding.btnShop.setOnClickListener {
//            binding.btnShop.background =
//                ContextCompat.getDrawable(requireContext(), R.drawable.button_color)
//            binding.btnTakeout.background =
//                ContextCompat.getDrawable(requireContext(), R.drawable.button_non_color)
//            isShop = true
//        }
//        binding.btnTakeout.setOnClickListener {
//            binding.btnTakeout.background =
//                ContextCompat.getDrawable(requireContext(), R.drawable.button_color)
//            binding.btnShop.background =
//                ContextCompat.getDrawable(requireContext(), R.drawable.button_non_color)
//            isShop = false
//        }
//        binding.btnOrder.setOnClickListener {
//            if (isShop) showDialogForOrderInShop()
//            else {
//                //거리가 200이상이라면
//                if (true) showDialogForOrderTakeoutOver200m()
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mainActivity.hideBottomNav(false)
//    }
//
//    private fun showDialogForOrderInShop() {
//        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
//        builder.setTitle("알림")
//        builder.setMessage(
//            "Table NFC를 먼저 찍어주세요.\n"
//        )
//        builder.setCancelable(true)
//        builder.setNegativeButton(
//            "취소"
//        ) { dialog, _ ->
//            dialog.cancel()
//            dialog.cancel()
//            showToast("주문이 취소되었습니다.")
//        }
//        builder.create().show()
//    }
//
//    private fun showDialogForOrderTakeoutOver200m() {
//        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
//        builder.setTitle("알림")
//        builder.setMessage(
//            "현재 고객님의 위치가 매장과 200m 이상 떨어져 있습니다.\n정말 주문하시겠습니까?"
//        )
//        builder.setCancelable(true)
//        builder.setPositiveButton("확인") { _, _ ->
//            completedOrder("Take Out 주문")
//        }
//        builder.setNegativeButton("취소") { dialog, _ ->
//            dialog.cancel()
//            showToast("주문이 취소되었습니다.")
//        }
//        builder.create().show()
//    }
//
//
//    private fun completedOrder(table: String) {
//
//        if (activityViewModel.shoppingList.size <= 0) {
//            showToast("장바구니가 비어 있습니다.")
//            return;
//        }
//
//        showToast("주문이 완료되었습니다.")
//
//
//    }
//
//    // 재주문할때는 parameter로 넘기기.
//    companion object {
//        @JvmStatic
//        fun newInstance(param: Int) =
//            ShoppingListFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ORDER_ID, param)
//                }
//            }
//    }
}