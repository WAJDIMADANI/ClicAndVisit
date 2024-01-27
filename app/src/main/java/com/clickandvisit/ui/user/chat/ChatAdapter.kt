package com.clickandvisit.ui.user.chat

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.chat.Discussion
import com.clickandvisit.global.listener.DataAdapterListener
import com.squareup.picasso.Picasso


class ChatAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<ChatViewHolder>(),
    DataAdapterListener<ArrayList<Discussion>> {

    private var list = arrayListOf<Discussion>()

    lateinit var viewModel: ChatViewModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder.create(parent, parent.context, picasso,viewModel.getCurrentUser(), viewModel)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(list[position], position)
    }


    override fun getItemCount() = list.size

    override fun setData(data: ArrayList<Discussion>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

}