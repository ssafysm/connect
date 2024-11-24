package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentShopDialogBinding
import com.ssafy.smartstore_jetpack.presentation.config.BaseDialogFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import com.ssafy.smartstore_jetpack.presentation.views.main.coupondetail.CouponDetailUiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ShopDialogFragment :
    BaseDialogFragment<FragmentShopDialogBinding>(R.layout.fragment_shop_dialog) {

    private val viewModel: MainViewModel by activityViewModels()
    private var nfcAdapter: NfcAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        nfcAdapter = NfcAdapter.getDefaultAdapter(requireContext())

        binding.ivShopDialog.setOnClickListener {
            dismiss()
        }

        lifecycleScope.launch {
            viewModel.shoppingUiEvent.collectLatest {
                if (it == ShoppingListUiEvent.Tagged) dismiss()
            }
            viewModel.couponDetailUiEvent.collectLatest {
                if (it == CouponDetailUiEvent.Tagged) dismiss()
            }
        }
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
                val textData = String(payload, 3, payload.size - 3)

                requireActivity().runOnUiThread {
                    val tableNumber = textData.split(":")[0].toInt()
                    viewModel.setTableNumber(tableNumber)
                    showToastMessage("${tableNumber}${getString(R.string.message_table_number_enroll)}")
                }

                it.close()
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    showToastMessage(getString(R.string.message_nfc_fail))
                }
            }
        }
    }
}
