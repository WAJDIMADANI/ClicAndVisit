package com.clickandvisit.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.global.listener.DataAdapterListener
import com.squareup.picasso.Picasso


class SearchAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<SearchViewHolder>(),
    DataAdapterListener<ArrayList<Property>> {

    private var list = arrayListOf<Property>()

    lateinit var viewModel: HomeViewModel

    var userId: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.create(parent, parent.context, picasso, viewModel)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(list[position], position, userId)
    }

    override fun getItemCount() = list.size

    override fun setData(data: ArrayList<Property>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

}