package com.clickandvisit.ui.ads.adsdetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.databinding.RoomItemBinding


class RoomHolder(
    private val binding: RoomItemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(value: String, position: Int) {

        binding.tvHome1.text = value
        binding.executePendingBindings()
    }

    companion object {
        fun create(
            parent: ViewGroup,
            context: Context
        ): RoomHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RoomItemBinding.inflate(inflater, parent, false)
            return RoomHolder(
                binding,
                context,
            )
        }
    }
}