package com.clickandvisit.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.ItemFavouriteBinding
import com.clickandvisit.global.listener.OnPropertyClickedListener
import com.denzcoskun.imageslider.models.SlideModel
import com.squareup.picasso.Picasso


class SearchViewHolder(
    private val binding: ItemFavouriteBinding,
    private val context: Context,
    private val picasso: Picasso,
    private val onPropertyClickedListener: OnPropertyClickedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(value: Property, position: Int) {

        if (value.visitNow) {
            binding.tvMeet.setBackgroundColor(context.getColor(R.color.green_pro_status_details))
            binding.tvMeet.text = context.getString(R.string.home_visit)
        } else {
            binding.tvMeet.setBackgroundColor(context.getColor(R.color.red_pro_status))
            binding.tvMeet.text = context.getString(R.string.home_meet)
        }
        binding.tvAdsName.text = value.title
        binding.tvPhotoCount.text = value.album.size.toString()
        binding.tvAdsPrice.text =
            value.getCategories() + value.getPriceNBR() + context.getString(R.string.home_details_euros)
        binding.tvAdsSpace.text =
            value.details.getRoomsNBR() + value.surface + context.getString(R.string.home_details_m_square)

/*
        DrawableCompat.setTint(
            DrawableCompat.wrap(binding.ivLike.drawable),
            ContextCompat.getColor(context, R.color.red_pro_status)
        )*/

        proVisibility(value)
        loadImages(value)

        binding.property = value
        binding.listener = onPropertyClickedListener

        binding.executePendingBindings()
    }

    private fun proVisibility(value: Property) {
        binding.tvPro.visibility = if (value.owner.isPro()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun loadImages(value: Property) {
        val imageList = ArrayList<SlideModel>()
        value.album.forEach {
            imageList.add(SlideModel(imageUrl = it))
        }
        binding.imageSlider.setImageList(imageList)
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