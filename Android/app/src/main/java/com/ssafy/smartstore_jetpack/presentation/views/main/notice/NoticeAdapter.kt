package com.ssafy.smartstore_jetpack.presentation.views.main.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore_jetpack.databinding.ListItemNoticeBinding
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import timber.log.Timber

class NoticeAdapter(private val viewModel: MainViewModel) :
    ListAdapter<String, RecyclerView.ViewHolder>(diffUtil) {

    class NoticeViewHolder(private val binding: ListItemNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notice: String, viewModel: MainViewModel) {
            binding.tvTitleNotify.text = notice
            Timber.d("Notice: $notice")
            val noticeContent = notice.split("\n")
            binding.tvTitleNotify.text = noticeContent[1]
            binding.tvMessageNotify.text = noticeContent[2]
            binding.tvTimeNotify.text = noticeContent[0]
            binding.ivNotify.setOnClickListener {
                viewModel.onClickNoticeDelete(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder =
        NoticeViewHolder(
            ListItemNoticeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NoticeViewHolder).bind(currentList[position], viewModel)
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<String>() {

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                (oldItem == newItem)

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                (oldItem.hashCode() == newItem.hashCode())
        }
    }
}