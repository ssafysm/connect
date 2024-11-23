package com.ssafy.smartstore_jetpack.presentation.views.main.menudetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore_jetpack.databinding.ListItemCommentBinding
import com.ssafy.smartstore_jetpack.domain.model.Comment
import com.ssafy.smartstore_jetpack.presentation.views.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class CommentAdapter(
    private val viewModel: MainViewModel,
    private val userId: String
) : ListAdapter<Comment, CommentAdapter.CommentHolder>(diffUtil) {

    inner class CommentHolder(private val binding: ListItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: Comment, viewModel: MainViewModel) {
            binding.comment = comment
            binding.vm = viewModel
            binding.tvRatingComment.text = comment.rating.toString()
            binding.btnModifyComment.visibility = View.VISIBLE
            binding.btnDeleteComment.visibility = View.VISIBLE
            binding.btnUpdateComment.visibility = View.GONE
            binding.btnCancelComment.visibility = View.GONE
            binding.etComment.visibility = View.GONE
            binding.tvCommentComment.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.IO).launch {
                if (comment.userId != userId) {
                    binding.btnUpdateComment.visibility = View.GONE
                    binding.btnCancelComment.visibility = View.GONE
                    binding.btnDeleteComment.visibility = View.GONE
                    binding.btnModifyComment.visibility = View.GONE
                }
            }

            binding.btnModifyComment.setOnClickListener {
                binding.btnModifyComment.visibility = View.GONE
                binding.btnDeleteComment.visibility = View.GONE
                binding.btnUpdateComment.visibility = View.VISIBLE
                binding.btnCancelComment.visibility = View.VISIBLE
                binding.etComment.visibility = View.VISIBLE
                binding.etComment.setText(binding.tvCommentComment.text)
                binding.tvCommentComment.visibility = View.GONE
            }

            binding.btnCancelComment.setOnClickListener {
                binding.btnModifyComment.visibility = View.VISIBLE
                binding.btnDeleteComment.visibility = View.VISIBLE
                binding.btnUpdateComment.visibility = View.GONE
                binding.btnCancelComment.visibility = View.GONE
                binding.etComment.visibility = View.GONE
                binding.tvCommentComment.visibility = View.VISIBLE
            }

            binding.btnUpdateComment.setOnClickListener {
                viewModel.onClickUpdateComment(
                    Comment(
                        id = comment.id,
                        userId = comment.userId,
                        productId = comment.productId,
                        rating = comment.rating,
                        comment = binding.etComment.text.toString(),
                        userName = comment.userName
                    )
                )
                binding.btnModifyComment.visibility = View.VISIBLE
                binding.btnDeleteComment.visibility = View.VISIBLE
                binding.btnUpdateComment.visibility = View.GONE
                binding.btnCancelComment.visibility = View.GONE
                binding.etComment.visibility = View.GONE
                binding.tvCommentComment.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder =
        CommentHolder(
            ListItemCommentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bind(currentList[position], viewModel)
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Comment>() {

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                (oldItem.id == newItem.id)

            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                (oldItem == newItem)
        }
    }
}

