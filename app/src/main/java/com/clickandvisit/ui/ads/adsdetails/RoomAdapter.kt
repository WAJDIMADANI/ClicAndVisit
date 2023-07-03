package com.clickandvisit.ui.ads.adsdetails

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.global.listener.DataAdapterListener

class RoomAdapter() :
    RecyclerView.Adapter<RoomHolder>(),
    DataAdapterListener<ArrayList<String>> {

    private var list = arrayListOf<String>()

    lateinit var holder: RoomHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomHolder {
        holder = RoomHolder.create(parent, parent.context)
        return holder
    }

    override fun onBindViewHolder(holder: RoomHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount() = list.size

    override fun setData(data: ArrayList<String>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }


}
