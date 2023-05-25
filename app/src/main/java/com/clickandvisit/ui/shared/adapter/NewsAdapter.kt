package com.clickandvisit.ui.shared.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.news.News
import com.clickandvisit.global.listener.DataAdapterListener
import com.clickandvisit.global.listener.OnItemClickedListener
import com.clickandvisit.ui.shared.viewholder.NewsViewHolder
import com.squareup.picasso.Picasso

class NewsAdapter(private val picasso: Picasso) : RecyclerView.Adapter<NewsViewHolder>(),
    DataAdapterListener<List<News>> {

    private val news = arrayListOf<News>()

    lateinit var listener: OnItemClickedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent, listener, picasso)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun setData(data: List<News>) {
        news.clear()
        news.addAll(data)
        notifyDataSetChanged()
    }
}
