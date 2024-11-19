package com.ssafy.smartstore_jetpack.presentation.views.main.orderdetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore_jetpack.databinding.ListItemOrderDetailListBinding
import com.ssafy.smartstore_jetpack.domain.model.OrderDetail
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.makeCommaWon

class OrderDetailListAdapter :
    ListAdapter<OrderDetail, OrderDetailListAdapter.OrderDetailListViewHolder>(diffUtil) {

    inner class OrderDetailListViewHolder(private val binding: ListItemOrderDetailListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindInfo(orderDetail: OrderDetail) {
            val unitPrice = orderDetail.unitPrice.split("원")[0].toInt()
            binding.orderDetail = orderDetail
            binding.tvCountItemOrderDetail.text = "${orderDetail.quantity}잔"
            binding.tvSumPriceItemOrderDetail.text = makeCommaWon(unitPrice * orderDetail.quantity)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailListViewHolder =
        OrderDetailListViewHolder(
            ListItemOrderDetailListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: OrderDetailListViewHolder, position: Int) {
        holder.bindInfo(currentList[position])
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<OrderDetail>() {

            override fun areContentsTheSame(oldItem: OrderDetail, newItem: OrderDetail): Boolean =
                (oldItem.id == newItem.id)

            override fun areItemsTheSame(oldItem: OrderDetail, newItem: OrderDetail): Boolean =
                (oldItem == newItem)
        }
    }
}