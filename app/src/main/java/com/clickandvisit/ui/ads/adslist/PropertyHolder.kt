package com.clickandvisit.ui.ads.adslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.ItemAdsBinding
import com.clickandvisit.global.listener.OnMyPropertyClickedListener
import com.denzcoskun.imageslider.constants.ScaleTypes
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

        if (value.category.isNullOrEmpty() && value.price.isNullOrEmpty()) {
            binding.tvAdsPrice.visibility = View.GONE
        } else if (value.category.isNullOrEmpty().not() && value.price.isNullOrEmpty()) {
            binding.tvAdsPrice.text = value.category
        } else if (value.category.isNullOrEmpty() && value.price.isNullOrEmpty().not()) {
            binding.tvAdsPrice.text = value.getPriceNBR()
        } else {
            binding.tvAdsPrice.text = value.getCategories() + value.getPriceNBR()
        }

        binding.tvAdsSpace.text =
            value.details.getRoomsNBR() + value.surface  + " " + context.getString(
                R.string.home_details_m_square
            )


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
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
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