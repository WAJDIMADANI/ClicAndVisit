package com.clickandvisit.ui.shared.viewholder

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.databinding.ItemCardFooterBinding
import com.clickandvisit.ui.menu.payment.OnAddCardClickListener


class CardFooterHolder(
    private val binding: ItemCardFooterBinding,
    private val onAddCardClickListener: OnAddCardClickListener?,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind() {

        binding.btnCardAdd.setOnClickListener {
            onAddCardClickListener?.onAddCardClickListener()
        }
        binding.executePendingBindings()
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onAddCardClickListener: OnAddCardClickListener?,
            context: Context
        ): CardFooterHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCardFooterBinding.inflate(inflater, parent, false)
            return CardFooterHolder(
                binding,
                onAddCardClickListener,
                context
            )
        }
    }


}
