package com.clickandvisit.ui.user.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.chat.Discussion
import com.clickandvisit.databinding.ItemChatBinding
import com.clickandvisit.global.listener.OnChatItemClickedListener
import com.clickandvisit.global.utils.toMediaUrl
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso


class ChatViewHolder(
    private val binding: ItemChatBinding,
    private val context: Context,
    private val picasso: Picasso,
    private val onChatItemClickedListener: OnChatItemClickedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(value: Discussion, position: Int) {

        binding.tvChatUserName.text = value.fromName
        binding.tvChatLastMsg.text = value.lastMessage
        binding.tvCityDate.text = value.property + " - " + value.date

        picasso.load(value.fromPicture.toMediaUrl())
            .fit()
            .centerInside()
            .into(binding.ivChatUserPhoto)

        binding.discussion = value
        binding.listener = onChatItemClickedListener

        binding.executePendingBindings()
    }

    companion object {
        fun create(
            parent: ViewGroup,
            context: Context,
            picasso: Picasso,
            onChatItemClickedListener: OnChatItemClickedListener
        ): ChatViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemChatBinding.inflate(inflater, parent, false)
            return ChatViewHolder(
                binding,
                context,
                picasso,
                onChatItemClickedListener
            )
        }
    }

}