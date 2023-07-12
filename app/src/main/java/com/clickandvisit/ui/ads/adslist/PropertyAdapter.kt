package com.clickandvisit.ui.ads.adslist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.global.listener.DataAdapterListener
import com.squareup.picasso.Picasso


class PropertyAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<PropertyHolder>(),
    DataAdapterListener<ArrayList<Property>> {

    var list = arrayListOf<Property>()

    lateinit var viewModel: AdsListViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyHolder {
        return PropertyHolder.create(parent, parent.context, picasso, viewModel)
    }

    override fun onBindViewHolder(holder: PropertyHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount() = list.size

    override fun setData(data: ArrayList<Property>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

}
