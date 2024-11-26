package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.smartstore_jetpack.databinding.ItemChatGptBinding
import com.ssafy.smartstore_jetpack.databinding.ItemChatUserBinding
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import io.noties.markwon.Markwon

class ChatAdapter(private val viewModel: MainViewModel) : ListAdapter<ChatMessage, RecyclerView.ViewHolder>(diffUtil) {

	class UserViewHolder(private val binding: ItemChatUserBinding,
						 private val markwon: Markwon
	) : RecyclerView.ViewHolder(binding.root) {

		fun bind(chat: ChatMessage, viewModel: MainViewModel) {
			binding.vm = viewModel
			binding.chatName.text = chat.senderName
			// Markwon을 사용하여 Markdown 처리
			markwon.setMarkdown(binding.chatText, chat.text)

			if (chat.imageUri != null) {
				binding.chatImage.visibility = View.VISIBLE
				Glide.with(binding.chatImage.context)
					.load(chat.imageUri)
					.into(binding.chatImage)
			} else {
				binding.chatImage.visibility = View.GONE
			}

			binding.chatIcon.visibility = View.VISIBLE
		}
	}

	class GptViewHolder(private val binding: ItemChatGptBinding,
						private val markwon: Markwon
	) : RecyclerView.ViewHolder(binding.root) {

		fun bind(chat: ChatMessage, viewModel: MainViewModel) {
			binding.vm = viewModel
			binding.chatName.text = chat.senderName
			// Markwon을 사용하여 Markdown 처리
			markwon.setMarkdown(binding.chatText, chat.text)

			if (chat.imageUri != null) {
				binding.chatImage.visibility = View.VISIBLE
				Glide.with(binding.chatImage.context)
					.load(chat.imageUri)
					.into(binding.chatImage)
			} else {
				binding.chatImage.visibility = View.GONE
			}

			binding.chatIcon.visibility = View.VISIBLE
		}
	}
	private lateinit var markwon: Markwon

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

		// Markwon 인스턴스를 생성 (Context 필요)
		if (!::markwon.isInitialized) {
			markwon = Markwon.create(parent.context)
		}
		return if (viewType == VIEW_TYPE_USER) {
			val binding = ItemChatUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			UserViewHolder(binding, markwon)
		} else {
			val binding = ItemChatGptBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			GptViewHolder(binding, markwon)
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val chat = currentList[position]
		if (holder is UserViewHolder) {
			holder.bind(chat, viewModel)
		} else if (holder is GptViewHolder) {
			holder.bind(chat, viewModel)
		}
	}

	override fun getItemViewType(position: Int): Int {
		return if (currentList[position].isSender) {
			VIEW_TYPE_USER
		} else {
			VIEW_TYPE_GPT
		}
	}

	companion object {

		private const val VIEW_TYPE_USER = 0
		private const val VIEW_TYPE_GPT = 1

		val diffUtil = object : DiffUtil.ItemCallback<ChatMessage>() {

			override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean =
				(oldItem == newItem)

			override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean =
				(oldItem.hashCode() == newItem.hashCode())
		}
	}
}
