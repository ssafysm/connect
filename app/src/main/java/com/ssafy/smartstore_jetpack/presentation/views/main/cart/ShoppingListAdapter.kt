package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.databinding.ListItemShoppingListBinding
import com.ssafy.smartstore_jetpack.domain.model.ShoppingCart
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.deleteComma
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.makeComma
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel

class ShoppingListAdapter(private val viewModel: MainViewModel) :
    ListAdapter<ShoppingCart, ShoppingListAdapter.ShoppingListHolder>(diffUtil) {

    inner class ShoppingListHolder(private val binding: ListItemShoppingListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindInfo(product: ShoppingCart, viewModel: MainViewModel) {
            binding.product = product
            binding.tvCountItemCart.text = product.menuCnt
            binding.tvPriceItemCart.text = makeComma(deleteComma(product.menuPrice))
            binding.btnDeleteItemCart.setOnClickListener {
                viewModel.onClickProductDelete(layoutPosition)
            }
            binding.ivAddItemCart.setOnClickListener {
                if (product.menuCnt != "99") {
                    viewModel.onClickProductAdd(layoutPosition)
                }
            }
            binding.ivAddItemCart.isEnabled = (product.menuCnt != "99")
            if (product.menuCnt != "1") {
                binding.ivRemoveItemCart.setBackgroundResource(R.drawable.ic_remove)
            } else {
                binding.ivRemoveItemCart.setBackgroundResource(R.drawable.ic_delete)
            }
            binding.ivRemoveItemCart.setOnClickListener {
                if (product.menuCnt != "1") {
                    viewModel.onClickProductRemove(layoutPosition)
                } else {
                    viewModel.onClickProductDelete(layoutPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListHolder =
        ShoppingListHolder(
            ListItemShoppingListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ShoppingListHolder, position: Int) {
        holder.bindInfo(currentList[position], viewModel)
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<ShoppingCart>() {

            override fun areContentsTheSame(oldItem: ShoppingCart, newItem: ShoppingCart): Boolean =
                (oldItem == newItem)

            override fun areItemsTheSame(oldItem: ShoppingCart, newItem: ShoppingCart): Boolean =
                (oldItem.menuId == newItem.menuId)
        }
    }
}