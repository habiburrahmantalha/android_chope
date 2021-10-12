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
import com.tree.chope.databinding.LayoutChatItemBinding

class ChatAdapter(var list: List<ChatHistory?>, private val viewModel: ChatViewModel): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ViewTypes.CHAT_LEFT.value -> ViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.layout_message_left, parent, false))
            ViewTypes.CHAT_RIGHT.value -> ViewHolder(
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
            is ViewHolder -> {
                list[position]?.let {
                    holder.bind(it, viewModel)
                }
            }
        }
    }

    fun updateList(list: List<ChatHistory?>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutChatItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data:Any, viewModel: Any){
            binding.setVariable(BR.chat, data)
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
        }
    }




}