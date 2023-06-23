package com.clickandvisit.ui.ads.adslist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.databinding.ItemAdsBinding
import com.clickandvisit.global.listener.OnPropertyClickedListener
import com.clickandvisit.ui.home.SearchViewHolder
import com.squareup.picasso.Picasso


class PropertyHolder(
    private val binding: ItemAdsBinding,
    private val context: Context,
    private val picasso: Picasso,
    private val onPropertyClickedListener: OnPropertyClickedListener
) : RecyclerView.ViewHolder(binding.root) {



    companion object {
        fun create(
            parent: ViewGroup,
            context: Context,
            picasso: Picasso,
            onPropertyClickedListener: OnPropertyClickedListener
        ): PropertyHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemAdsBinding.inflate(inflater, parent, false)
            return PropertyHolder(
                binding,
                context,
                picasso,
                onPropertyClickedListener
            )
        }
    }

}