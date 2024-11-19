package com.ssafy.smartstore_jetpack.presentation.views.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore_jetpack.databinding.ListItemEventBinding

class EventAdapter : ListAdapter<EventUiState, RecyclerView.ViewHolder>(diffUtil) {

    class EventViewHolder(private val binding: ListItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventUiState.EventItem) {
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
        (holder as EventViewHolder).bind(currentList[position] as EventUiState.EventItem)
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<EventUiState>() {

            override fun areContentsTheSame(oldItem: EventUiState, newItem: EventUiState): Boolean =
                (oldItem == newItem)

            override fun areItemsTheSame(oldItem: EventUiState, newItem: EventUiState): Boolean =
                (oldItem.id == newItem.id)
        }
    }
}