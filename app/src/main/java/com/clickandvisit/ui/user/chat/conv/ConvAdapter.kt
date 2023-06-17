package com.clickandvisit.ui.user.chat.conv

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.chat.Message
import com.clickandvisit.global.listener.DataAdapterListener
import com.squareup.picasso.Picasso


class ConvAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<ConvViewHolder>(),
    DataAdapterListener<ArrayList<Message>> {

    private var list = arrayListOf<Message>()

    lateinit var viewModel: ConvViewModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvViewHolder {
        return ConvViewHolder.create(parent, parent.context, picasso, viewModel.getCurrentUserId())
    }

    override fun onBindViewHolder(holder: ConvViewHolder, position: Int) {
        holder.bind(list[position], position)
    }


    override fun getItemCount() = list.size

    override fun setData(data: ArrayList<Message>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

}