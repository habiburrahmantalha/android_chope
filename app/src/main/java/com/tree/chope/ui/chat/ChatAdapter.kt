package com.tree.chope.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tree.chope.BR
import com.tree.chope.R
import com.tree.chope.ViewHolderShimmer
import com.tree.chope.ViewTypes
import com.tree.chope.backend.data.ChatHistory
import com.tree.chope.backend.data.Message
import com.tree.chope.databinding.LayoutChatItemBinding
import com.tree.chope.databinding.LayoutMessageLeftBinding
import com.tree.chope.databinding.LayoutMessageRightBinding

class ChatAdapter(var list: List<Message?>, private val viewModel: ChatViewModel): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ViewTypes.CHAT_LEFT.value -> ViewHolderLeft(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.layout_message_left, parent, false))
            ViewTypes.CHAT_RIGHT.value -> ViewHolderRight(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    R.layout.layout_message_right, parent, false))
            else -> ViewHolderShimmer(LayoutInflater.from(parent.context).inflate(
                R.layout.layout_shimmer_chat,
                parent,
                false
            )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            list.isEmpty() -> ViewTypes.SHIMMER.value
            list[position]?.userId == 1 -> ViewTypes.CHAT_RIGHT.value
            else -> ViewTypes.CHAT_LEFT.value
        }
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) 2 else  list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderLeft -> {
                list[position]?.let {
                    holder.bind(it, viewModel)
                }
            }
            is ViewHolderRight -> {
                list[position]?.let {
                    holder.bind(it, viewModel)
                }
            }
        }
    }

    fun updateList(list: List<Message?>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolderLeft(private val binding: LayoutMessageLeftBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data:Any, viewModel: Any){
            binding.setVariable(BR.message, data)
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
        }
    }

    inner class ViewHolderRight(private val binding: LayoutMessageRightBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data:Any, viewModel: Any){
            binding.setVariable(BR.message, data)
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
        }
    }

}