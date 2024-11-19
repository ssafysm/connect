package com.ssafy.smartstore_jetpack.presentation.views.main.home

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ssafy.smartstore_jetpack.app.ApplicationClass
import com.ssafy.smartstore_jetpack.databinding.ListItemLatestOrderBinding
import com.ssafy.smartstore_jetpack.domain.model.Order
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.dateFormat
import com.ssafy.smartstore_jetpack.presentation.util.CommonUtils.makeComma
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
class OrderAdapter(private val viewModel: MainViewModel) :
    ListAdapter<Order, OrderAdapter.OrderViewHolder>(diffUtil) {

    inner class OrderViewHolder(private val binding: ListItemLatestOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(order: Order, viewModel: MainViewModel) {
            binding.order = order
            binding.vm = viewModel
            binding.tvOrderNumberOrder.text = "주문번호 : ${order.id}"
            val totalCount = order.details.sumOf { it.quantity }
            binding.tvOrderListOrder.text = when (totalCount) {
                1 -> order.details.first().productName

                else -> "${order.details.first().productName} 외 ${totalCount - 1}잔"
            }
            val totalPrice = order.details.sumOf { it.unitPrice.toInt() * it.quantity }
            binding.tvPriceOrder.text = makeComma(totalPrice)
            binding.ivOrder.load("${ApplicationClass.MENU_IMGS_URL}${order.details.first().img}") {
                transformations(RoundedCornersTransformation(10F))
            }
            binding.tvDateOrder.text = dateFormat(order.orderTime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder =
        OrderViewHolder(
            ListItemLatestOrderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(currentList[position], viewModel)
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Order>() {

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean =
                (oldItem.id == newItem.id)

            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean =
                (oldItem == newItem)
        }
    }
}
