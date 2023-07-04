package com.clickandvisit.ui.shared.bottomsheet.maps

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.ButtomSheetMapsBinding
import com.clickandvisit.global.listener.OnMapsClickedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso


class CustomMapsBottomSheet(
    private val myContext: Context,
    private val property: Property,
    private val onMapsClickedListener: OnMapsClickedListener,
    private val actionBlock: (() -> Unit)? = null,
    private val dismissActionBlock: (() -> Unit)? = null
) : BottomSheetDialog(myContext) {

    private lateinit var binding: ButtomSheetMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(myContext),
                R.layout.buttom_sheet_maps,
                null,
                true
            )

        binding.bottomsheet = this
        binding.tvAdsName.text = property.title.toUpperCase()

        Picasso.get().load(property.mainPhoto).fit().centerCrop().into(binding.imageSlider)


        if (property.category.isNullOrEmpty() && property.price.isNullOrEmpty()) {
            binding.tvAdsPrice.visibility = View.GONE
        } else if (property.category.isNullOrEmpty().not() && property.price.isNullOrEmpty()) {
            binding.tvAdsPrice.text = property.category
        } else if (property.category.isNullOrEmpty() && property.price.isNullOrEmpty().not()) {
            binding.tvAdsPrice.text = property.getPriceNBR()
        } else {
            binding.tvAdsPrice.text = property.getCategories() + property.getPriceNBR()
        }

        binding.tvAdsSpace.text =
            property.details.getRoomsNBR() + property.surface + " " + context.getString(
                R.string.home_details_m_square
            )


        binding.tvPhotoCount.text = if (property.mainPhoto.isNullOrEmpty().not()) {
            property.album.size.plus(1).toString()
        } else {
            property.album.size.toString()
        }

        initializeListener()

        setCancelable(false)
        binding.executePendingBindings()
        setContentView(binding.root)
        window?.setGravity(Gravity.BOTTOM)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun initializeListener() {
        setOnDismissListener {
            dismissActionBlock?.invoke()
        }

        binding.ivHide.setOnClickListener {
            actionBlock?.invoke()
            dismiss()
        }

        binding.tvMeet.setOnClickListener {
            onMapsClickedListener.onMeetClicked(property)
        }

        binding.clContainer.setOnClickListener {
            onMapsClickedListener.onItemClicked(property)
        }

    }

    override fun onStart() {
        super.onStart()
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}