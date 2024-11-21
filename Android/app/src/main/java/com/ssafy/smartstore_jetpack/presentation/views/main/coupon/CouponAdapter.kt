package com.ssafy.smartstore_jetpack.presentation.views.main.coupon

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore_jetpack.databinding.ListItemCouponBinding
import com.ssafy.smartstore_jetpack.domain.model.Coupon
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel

class CouponAdapter(private val viewModel: MainViewModel) : ListAdapter<Coupon, RecyclerView.ViewHolder>(diffUtil) {

    class CouponViewHolder(private val binding: ListItemCouponBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(coupon: Coupon, viewModel: MainViewModel) {
            binding.coupon = coupon
            binding.vm = viewModel
            binding.tvPriceItemCoupon.text = "${coupon.price} 할인"
            binding.tvTimeItemCoupon.text = "만료일 : ${coupon.couponTime}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CouponViewHolder(
            ListItemCouponBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CouponViewHolder).bind(currentList[position], viewModel)
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Coupon>() {

            override fun areContentsTheSame(oldItem: Coupon, newItem: Coupon): Boolean =
                (oldItem == newItem)

            override fun areItemsTheSame(oldItem: Coupon, newItem: Coupon): Boolean =
                (oldItem.id == newItem.id)
        }
    }
}