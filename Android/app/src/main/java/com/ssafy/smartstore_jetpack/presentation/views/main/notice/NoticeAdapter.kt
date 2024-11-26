package com.ssafy.smartstore_jetpack.presentation.views.main.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore_jetpack.databinding.ListItemNoticeBinding
import com.ssafy.smartstore_jetpack.domain.model.Alarm
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import io.noties.markwon.Markwon

class NoticeAdapter(private val viewModel: MainViewModel) :
    ListAdapter<Alarm, RecyclerView.ViewHolder>(diffUtil) {

    private lateinit var markwon: Markwon

    class NoticeViewHolder(
        private val binding: ListItemNoticeBinding,
        private val markwon: Markwon
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(alarm: Alarm, viewModel: MainViewModel) {
            binding.vm = viewModel
            binding.alarm = alarm
            // Markwon을 사용하여 Markdown 처리
            markwon.setMarkdown(binding.tvMessageNotify, alarm.content)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        // Markwon 인스턴스를 생성 (Context 필요)
        if (!::markwon.isInitialized) {
            markwon = Markwon.create(parent.context)
        }
        val binding = ListItemNoticeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NoticeViewHolder(binding, markwon)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NoticeViewHolder).bind(currentList[position], viewModel)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Alarm>() {
            override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean =
                (oldItem == newItem)

            override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean =
                (oldItem.id == newItem.id)
        }
    }
}
