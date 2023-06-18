package com.clickandvisit.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.ItemFavouriteBinding
import com.clickandvisit.global.listener.OnPropertyClickedListener
import com.squareup.picasso.Picasso


class SearchViewHolder(
    private val binding: ItemFavouriteBinding,
    private val context: Context,
    private val picasso: Picasso,
    private val onPropertyClickedListener: OnPropertyClickedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(value: Property, position: Int) {

        binding.tvAdsName.text = value.title
        binding.tvPhotoCount.text = value.album.size.toString()
        binding.tvAdsPrice.text = value.category + " - " + value.price
        binding.tvAdsSpace.text = "    - " + value.surface
        binding.tvPro.visibility = if (value.owner.isPro()) {
            View.VISIBLE
        } else {
            View.GONE
        }

/*        binding.tvChatUserName.text = value.fromName
        binding.tvChatLastMsg.text = value.lastMessage
        binding.tvCityDate.text = value.property + " - " + value.date

        picasso.load(value.fromPicture.toMediaUrl())
            .memoryPolicy(
                MemoryPolicy.NO_CACHE,
                MemoryPolicy.NO_STORE
            )
            .fit()
            .centerInside()
            .into(binding.ivChatUserPhoto)*/

        binding.property = value
        binding.listener = onPropertyClickedListener

        binding.executePendingBindings()
    }

    companion object {
        fun create(
            parent: ViewGroup,
            context: Context,
            picasso: Picasso,
            onPropertyClickedListener: OnPropertyClickedListener
        ): SearchViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemFavouriteBinding.inflate(inflater, parent, false)
            return SearchViewHolder(
                binding,
                context,
                picasso,
                onPropertyClickedListener
            )
        }
    }

}