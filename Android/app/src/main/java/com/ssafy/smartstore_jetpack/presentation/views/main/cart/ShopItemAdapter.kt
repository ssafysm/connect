package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore_jetpack.databinding.ListItemShopBinding
import com.ssafy.smartstore_jetpack.domain.model.Shop

class ShopItemAdapter(private val clickListener: ShoppingListClickListener) :
    ListAdapter<Shop, RecyclerView.ViewHolder>(diffUtil) {

    class ShopItemViewHolder(private val binding: ListItemShopBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shop: Shop, clickListener: ShoppingListClickListener) {
            binding.shop = shop
            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ShopItemViewHolder(
            ListItemShopBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ShopItemViewHolder).bind(currentList[position], clickListener)
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Shop>() {

            override fun areContentsTheSame(oldItem: Shop, newItem: Shop): Boolean =
                (oldItem == newItem)

            override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean =
                (oldItem.id == newItem.id)
        }
    }
}