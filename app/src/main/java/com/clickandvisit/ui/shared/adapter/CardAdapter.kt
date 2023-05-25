package com.clickandvisit.ui.shared.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.Card
import com.clickandvisit.global.listener.DataAdapterListener
import com.clickandvisit.ui.menu.payment.OnAddCardClickListener
import com.clickandvisit.ui.menu.payment.OnEditClickListener
import com.clickandvisit.ui.menu.payment.PaymentViewModel
import com.clickandvisit.ui.shared.viewholder.CardFooterHolder
import com.clickandvisit.ui.shared.viewholder.CardHolder

private const val VIEW_TYPE = 1
private const val FOOTER_VIEW_TYPE = 2

class CardAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    DataAdapterListener<ArrayList<Card>> {

    private var cardValues = arrayListOf<Card>()

    lateinit var viewModel: PaymentViewModel

    var onEditClickListener: OnEditClickListener? = null

    var onAddCardClickListener: OnAddCardClickListener? = null


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            cardValues.size -> FOOTER_VIEW_TYPE
            else -> VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FOOTER_VIEW_TYPE) {
            CardFooterHolder.create(
                parent,
                onAddCardClickListener,
                parent.context
            )
        } else {
            CardHolder.create(
                parent,
                onEditClickListener,
                parent.context
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            cardValues.size -> (holder as CardFooterHolder).bind()
            else -> (holder as CardHolder).bind(cardValues[position])
        }
    }

    override fun getItemCount() = cardValues.size + 1

    override fun setData(data: ArrayList<Card>) {
        cardValues.clear()
        cardValues.addAll(data)
        notifyDataSetChanged()
    }

}