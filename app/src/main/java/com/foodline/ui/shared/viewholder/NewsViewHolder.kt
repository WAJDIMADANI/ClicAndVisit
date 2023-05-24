package com.foodline.ui.shared.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.foodline.R
import com.foodline.data.model.news.News
import com.foodline.databinding.ItemNewsBinding
import com.foodline.global.listener.OnItemClickedListener
import com.squareup.picasso.Picasso

class NewsViewHolder(
    private val binding: ItemNewsBinding,
    private val onItemClickedListener: OnItemClickedListener,
    private val picasso: Picasso
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(news: News) {
        binding.picasso = picasso
        binding.title = news.title
        binding.imageUrl = news.image
        binding.onItemClickedListener = onItemClickedListener
        binding.placeHolder = AppCompatResources.getDrawable(binding.root.context, R.mipmap.ic_launcher)
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup, onItemClickedListener: OnItemClickedListener, picasso: Picasso): NewsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemNewsBinding.inflate(inflater, parent, false)
            return NewsViewHolder(
                binding,
                onItemClickedListener,
                picasso
            )
        }
    }
}