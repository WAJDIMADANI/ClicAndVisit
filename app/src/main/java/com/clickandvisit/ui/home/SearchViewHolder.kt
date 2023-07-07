package com.clickandvisit.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.ItemFavouriteBinding
import com.clickandvisit.global.listener.OnPropertyClickedListener
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
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
        binding.tvAdsName.text = value.title.toUpperCase()
        if (value.mainPhoto.isNullOrEmpty().not()) {
            if (value.album.isNullOrEmpty()){
                binding.tvPhotoCount.text = "1"
            }else{
                binding.tvPhotoCount.text = value.album?.size?.plus(1).toString()
            }
        } else {
            binding.tvPhotoCount.text = value.album?.size.toString()
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
            value.details.getRoomsNBR() + value.surface + " " + context.getString(
                R.string.home_details_m_square
            )


        if(value.isFavorite){
            binding.ivLike.background = context.getDrawable(R.drawable.ic_like_on)
        }else{
            binding.ivLike.background = context.getDrawable(R.drawable.ic_like)
        }

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
        if (value.mainPhoto.isNullOrEmpty().not()) {
            imageList.add(SlideModel(imageUrl = value.mainPhoto))
        }
        value.album?.forEach {
            imageList.add(SlideModel(imageUrl = it))
        }
        binding.imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)

        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                binding.clContainer.performClick()
            }

            override fun onItemSelected(position: Int) {
                binding.clContainer.performClick()
            }
        })
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