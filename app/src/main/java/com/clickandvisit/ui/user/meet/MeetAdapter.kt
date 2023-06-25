package com.clickandvisit.ui.user.meet

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.reservation.Reservation
import com.clickandvisit.global.listener.DataAdapterListener
import com.squareup.picasso.Picasso


class MeetAdapter(private val picasso: Picasso) :
    RecyclerView.Adapter<MeetHolder>(),
    DataAdapterListener<ArrayList<Reservation>> {

    private var list = arrayListOf<Reservation>()

    lateinit var viewModel: MeetViewModel

    var isFromMeet: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetHolder {
        return MeetHolder.create(parent, parent.context, picasso, viewModel, isFromMeet)
    }

    override fun onBindViewHolder(holder: MeetHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount() = list.size

    override fun setData(data: ArrayList<Reservation>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

}
