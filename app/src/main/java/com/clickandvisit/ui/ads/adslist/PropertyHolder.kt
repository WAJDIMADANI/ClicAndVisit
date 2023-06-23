package com.clickandvisit.ui.ads.adslist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.ItemAdsBinding
import com.clickandvisit.global.listener.OnMyPropertyClickedListener
import com.denzcoskun.imageslider.models.SlideModel
import com.squareup.picasso.Picasso


class PropertyHolder(
    private val binding: ItemAdsBinding,
    private val context: Context,
    private val picasso: Picasso,
    private val onMyPropertyClickedListener: OnMyPropertyClickedListener
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(value: Property, position: Int) {
        binding.tvAdsName.text = value.title
        binding.tvPhotoCount.text = if (value.mainPhoto.isNullOrEmpty().not()) {
            value.album.size.plus(1).toString()
        } else {
            value.album.size.toString()
        }
        binding.tvAdsPrice.text =
            value.getCategories() + value.getPriceNBR() + context.getString(R.string.home_details_euros)

        loadImages(value)

        binding.property = value
        binding.listener = onMyPropertyClickedListener

        binding.executePendingBindings()
    }

    private fun loadImages(value: Property) {
        val imageList = ArrayList<SlideModel>()
        if (value.mainPhoto.isNullOrEmpty().not()) {
            imageList.add(SlideModel(imageUrl = value.mainPhoto))
        }

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
            onMyPropertyClickedListener: OnMyPropertyClickedListener
        ): PropertyHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemAdsBinding.inflate(inflater, parent, false)
            return PropertyHolder(
                binding,
                context,
                picasso,
                onMyPropertyClickedListener
            )
        }
    }

}