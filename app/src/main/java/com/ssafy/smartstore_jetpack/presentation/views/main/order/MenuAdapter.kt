package com.ssafy.smartstore_jetpack.presentation.views.main.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore_jetpack.databinding.ListItemMenuBinding
import com.ssafy.smartstore_jetpack.domain.model.Product
import timber.log.Timber

class MenuAdapter(
    private val products: List<Product>,
    private val clickListener: MenuClickListener
) : ListAdapter<Product, MenuAdapter.MenuViewHolder>(diffUtil) {

    inner class MenuViewHolder(private val binding: ListItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindInfo(product: Product, clickListener: MenuClickListener) {
            Timber.d("Product: $product")
            binding.product = product
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                clickListener.onClickSomeProduct(product, binding.ivMenu)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder =
        MenuViewHolder(
            ListItemMenuBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bindInfo(products[position], clickListener)
    }

    override fun getItemCount(): Int = products.size

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Product>() {

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                (oldItem == newItem)

            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                (oldItem.id == newItem.id)
        }
    }
}

