package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore_jetpack.databinding.ListItemShoppingListBinding
import com.ssafy.smartstore_jetpack.domain.model.ShoppingCart
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.makeComma

class ShoppingListAdapter(private val clickListener: ShoppingListClickListener) :
    ListAdapter<ShoppingCart, ShoppingListAdapter.ShoppingListHolder>(diffUtil) {

    inner class ShoppingListHolder(private val binding: ListItemShoppingListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindInfo(product: ShoppingCart, clickListener: ShoppingListClickListener) {
            binding.product = product
            binding.tvCountItemCart.text = "${product.menuCnt}잔"
            binding.tvPriceItemCart.text = makeComma(product.menuPrice.toInt())
            binding.tvSumPriceItemCart.text = makeComma(product.totalPrice.toInt())
            binding.clCart.setOnClickListener {
                clickListener.onClickProductDelete(layoutPosition)
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
        holder.bindInfo(currentList[position], clickListener)
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<ShoppingCart>() {

            override fun areContentsTheSame(oldItem: ShoppingCart, newItem: ShoppingCart): Boolean =
                (oldItem.menuId == newItem.menuId)

            override fun areItemsTheSame(oldItem: ShoppingCart, newItem: ShoppingCart): Boolean =
                (oldItem == newItem)
        }
    }
}