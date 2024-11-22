package com.ssafy.smartstore_jetpack.presentation.views.main.coupondetail

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentCouponDetailBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.util.BlurHelper.applyBlur
import com.ssafy.smartstore_jetpack.presentation.util.BlurHelper.clearBlur
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CouponDetailFragment :
    BaseFragment<FragmentCouponDetailBinding>(R.layout.fragment_coupon_detail) {

    private val viewModel: MainViewModel by activityViewModels()
    private var nfcAdapter: NfcAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        nfcAdapter = NfcAdapter.getDefaultAdapter(requireContext())

        collectLatestFlow(viewModel.couponDetailUiEvent) { handleUiEvent(it) }
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

    override fun onStop() {
        super.onStop()

        nfcAdapter?.disableReaderMode(requireActivity())
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

    private fun handleUiEvent(event: CouponDetailUiEvent) = when (event) {
        is CouponDetailUiEvent.CouponShop -> {
            findNavController().navigateSafely(R.id.action_coupon_detail_to_shop_select)
            applyBlur(binding.fragmentCouponDetail, 20F)
        }

        is CouponDetailUiEvent.CouponTakeOut -> {
            findNavController().navigateSafely(R.id.action_coupon_detail_to_shop_select)
            applyBlur(binding.fragmentCouponDetail, 20F)
        }

        is CouponDetailUiEvent.FinishCouponOrder -> {
            Toast.makeText(
                requireContext(),
                "주문에 성공했어요! 주문 번호는 ${event.orderId}번이에요.",
                Toast.LENGTH_SHORT
            ).show()
            requireActivity().supportFragmentManager.popBackStack()
            clearBlur(binding.fragmentCouponDetail)
        }

        is CouponDetailUiEvent.CouponOrderFail -> {
            Toast.makeText(requireContext(), "주문에 실패했어요ㅠㅠ", Toast.LENGTH_SHORT).show()
            clearBlur(binding.fragmentCouponDetail)
        }

        else -> {}
    }
}