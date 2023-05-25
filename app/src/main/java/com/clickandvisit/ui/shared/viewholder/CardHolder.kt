package com.clickandvisit.ui.shared.viewholder

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.Card
import com.clickandvisit.databinding.ItemPaymentBinding
import com.clickandvisit.ui.menu.payment.OnEditClickListener

class CardHolder(
    private val binding: ItemPaymentBinding,
    private val onEditClickListener: OnEditClickListener?,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(
        card: Card
    ) {
        binding.tvCardNumber.text = card.number
        binding.llEdit.setOnClickListener {
            onEditClickListener?.onItemClickListener(card)
        }
        binding.executePendingBindings()
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onEditClickListener: OnEditClickListener?,
            context: Context
        ): CardHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemPaymentBinding.inflate(inflater, parent, false)
            return CardHolder(
                binding,
                onEditClickListener,
                context
            )
        }
    }

}