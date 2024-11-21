package com.ssafy.smartstore_jetpack.presentation.views.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore_jetpack.databinding.ListItemEventBinding
import com.ssafy.smartstore_jetpack.domain.model.Event

class EventAdapter : ListAdapter<Event, RecyclerView.ViewHolder>(diffUtil) {

    class EventViewHolder(private val binding: ListItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.event = event
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        EventViewHolder(
            ListItemEventBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EventViewHolder).bind(currentList[position])
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Event>() {

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
                (oldItem == newItem)

            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
                (oldItem.id == newItem.id)
        }
    }
}