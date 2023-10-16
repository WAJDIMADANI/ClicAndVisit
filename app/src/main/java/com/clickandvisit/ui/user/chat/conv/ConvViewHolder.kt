package com.clickandvisit.ui.user.chat.conv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.chat.Message
import com.clickandvisit.databinding.ItemConvBinding
import com.clickandvisit.global.utils.toMediaUrl
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso


class ConvViewHolder(
    private val binding: ItemConvBinding,
    private val context: Context,
    private val picasso: Picasso,
    private val currentUserId: Int
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(value: Message, position: Int) {

        if (value.authorId == currentUserId) {
            binding.tvChatMsgOther.visibility = View.GONE
            binding.ivChatUserPhoto.visibility = View.GONE
            binding.tvChatMsgMe.text = value.message
        } else {
            binding.tvChatMsgMe.visibility = View.GONE

            binding.tvChatMsgOther.text = value.message

            picasso.load(value.authorPhoto.toMediaUrl())
                .fit()
                .centerInside()
                .into(binding.ivChatUserPhoto)
        }

        binding.executePendingBindings()
    }

    companion object {
        fun create(
            parent: ViewGroup,
            context: Context,
            picasso: Picasso,
            currentUserId: Int
        ): ConvViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemConvBinding.inflate(inflater, parent, false)
            return ConvViewHolder(
                binding,
                context,
                picasso,
                currentUserId
            )
        }
    }

}