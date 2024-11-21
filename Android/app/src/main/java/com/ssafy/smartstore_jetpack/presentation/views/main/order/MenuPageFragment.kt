package com.ssafy.smartstore_jetpack.presentation.views.main.order

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.FragmentMenuPageBinding
import com.ssafy.smartstore_jetpack.domain.model.Product
import com.ssafy.smartstore_jetpack.presentation.config.BaseFragment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MenuPageFragment : BaseFragment<FragmentMenuPageBinding>(R.layout.fragment_menu_page) {

    private val viewModel: MainViewModel by activityViewModels()
    private var recyclerViewState: Parcelable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        arguments?.let { argument ->
            val menuType = argument.getInt("menu_type")
            lifecycleScope.launch {
                with(binding.rvOrder) {
                    viewModel.products.collectLatest { products ->
                        Timber.d("Index: ${menuType}, Products: ${products[menuType]}")
                        recyclerViewState = binding.rvOrder.layoutManager?.onSaveInstanceState()
                        adapter = MenuAdapter(products[menuType], object : MenuClickListener {

                            override fun onClickSomeProduct(product: Product, sharedView: View) {
                                viewModel.onClickProduct(product)

                                lifecycleScope.launch {
                                    viewModel.selectedProduct.collectLatest {
                                        if (viewModel.selectedProduct.value?.id == product.id) {
                                            val extras =
                                                FragmentNavigatorExtras(sharedView to "menu_detail_${product.id}")

                                            if (findNavController().currentDestination?.id != R.id.fragment_detail) {
                                                findNavController().navigate(
                                                    R.id.fragment_detail,
                                                    null,
                                                    NavOptions.Builder()
                                                        .setLaunchSingleTop(true)
                                                        .setPopUpTo(R.id.fragment_order, false)
                                                        .build(),
                                                    extras
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        })
                        setHasFixedSize(false)
                        layoutManager?.onRestoreInstanceState(recyclerViewState)
                    }
                }
            }
        }
    }

    companion object {

        fun newInstance(itemId: Long) = MenuPageFragment().apply {
            arguments = Bundle().apply {
                putInt("menu_type", itemId.toInt())
            }
        }
    }
}