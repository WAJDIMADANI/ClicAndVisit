package com.clickandvisit.ui.ads.addads.stepfour

import android.net.Uri
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.global.listener.DataAdapterListener
import com.squareup.picasso.Picasso


class ImageAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<ImageHolder>(),
    DataAdapterListener<ArrayList<Uri>> {

    private var list = arrayListOf<Uri>()

    lateinit var holder: ImageHolder

    lateinit var viewModel: FourViewModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        holder = ImageHolder.create(parent, parent.context, picasso, viewModel)
        return holder
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount() = list.size

    override fun setData(data: ArrayList<Uri>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }


}
