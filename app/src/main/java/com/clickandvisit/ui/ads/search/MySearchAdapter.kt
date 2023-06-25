package com.clickandvisit.ui.ads.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.property.SearchData
import com.clickandvisit.global.listener.DataAdapterListener
import com.squareup.picasso.Picasso


class MySearchAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<MySearchHolder>(),
    DataAdapterListener<ArrayList<SearchData>> {

    private var list = arrayListOf<SearchData>()

    lateinit var viewModel: SearchViewModel

    lateinit var holder: MySearchHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySearchHolder {
        holder =  MySearchHolder.create(parent, parent.context, picasso, viewModel)
        return holder
    }

    override fun onBindViewHolder(holder: MySearchHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount() = list.size

    override fun setData(data: ArrayList<SearchData>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }


}
