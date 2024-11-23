package com.ssafy.smartstore_jetpack.presentation.views.main.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.app.ApplicationClass
import com.ssafy.smartstore_jetpack.databinding.ListItemShopBinding
import com.ssafy.smartstore_jetpack.domain.model.Shop

class ShopItemAdapter(private val clickListener: ShoppingListClickListener) :
    ListAdapter<Shop, RecyclerView.ViewHolder>(diffUtil) {

    class ShopItemViewHolder(private val binding: ListItemShopBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(shop: Shop, clickListener: ShoppingListClickListener) {
            binding.shop = shop
            when (shop.distance) {
                -1F -> {
                    binding.tvDistanceItemShop.text = "거리 계산 중..."
                    binding.tvDistanceItemShop.setTextColor(ApplicationClass.myContext().getColor(R.color.neutral_70))
                }

                in 0F .. 199F -> {
                    binding.tvDistanceItemShop.text = "${shop.distance.toInt()}m"
                    binding.tvDistanceItemShop.setTextColor(ApplicationClass.myContext().getColor(R.color.neutral_100))
                }

                in 200F .. 999F -> {
                    binding.tvDistanceItemShop.text = "${shop.distance.toInt()}m"
                    binding.tvDistanceItemShop.setTextColor(ApplicationClass.myContext().getColor(R.color.sub_cancel))
                }

                else -> {
                    val kmDistance = String.format("%.1f", shop.distance / 1000F)
                    binding.tvDistanceItemShop.text = "${kmDistance}km"
                    binding.tvDistanceItemShop.setTextColor(ApplicationClass.myContext().getColor(R.color.sub_cancel))
                }
            }
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