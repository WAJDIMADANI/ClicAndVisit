package com.clickandvisit.ui.user.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.chat.Discussion
import com.clickandvisit.databinding.ItemChatBinding
import com.clickandvisit.global.listener.OnChatItemClickedListener
import com.clickandvisit.global.utils.toMediaUrl
import com.squareup.picasso.Picasso


class ChatViewHolder(
    private val binding: ItemChatBinding,
    private val context: Context,
    private val picasso: Picasso,
    private val currentUserId: Int,
    private val onChatItemClickedListener: OnChatItemClickedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(value: Discussion, position: Int) {


        binding.tvChatLastMsg.text = value.lastMessage
        binding.tvCityDate.text = if (value.property.isNullOrEmpty()) {
            value.date
        } else {
            value.property + " - " + value.date
        }

        if (currentUserId == value.from.toInt()){
            binding.tvChatUserName.text = value.toName

            picasso.load(value.toPicture.toMediaUrl())
                .fit()
                .centerInside()
                .into(binding.ivChatUserPhoto)

        }else{
            binding.tvChatUserName.text = value.fromName

            picasso.load(value.fromPicture.toMediaUrl())
                .fit()
                .centerInside()
                .into(binding.ivChatUserPhoto)

        }


        binding.discussion = value
        binding.listener = onChatItemClickedListener

        binding.executePendingBindings()
    }

    companion object {
        fun create(
            parent: ViewGroup,
            context: Context,
            picasso: Picasso,
            currentUserId: Int,
            onChatItemClickedListener: OnChatItemClickedListener
        ): ChatViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemChatBinding.inflate(inflater, parent, false)
            return ChatViewHolder(
                binding,
                context,
                picasso,
                currentUserId,
                onChatItemClickedListener
            )
        }
    }

}