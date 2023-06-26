package com.clickandvisit.ui.ads.adsdetails

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.ActivityAdsDetailsBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.observeOnlyNotNull
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdsDetailsActivity : BaseActivity() {

    private val viewModel: AdsDetailsViewModel by viewModels()

    private lateinit var binding: ActivityAdsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_ads_details
            )
        registerBindingAndBaseObservers(binding)


        viewModel.property.observeOnlyNotNull(this) {
            loadImages(it)
        }
    }

    private fun loadImages(value: Property) {
        val imageList = ArrayList<SlideModel>()
        if (value.mainPhoto.isNullOrEmpty().not()) {
            imageList.add(SlideModel(imageUrl = value.mainPhoto))
        }

        value.album.forEach {
            imageList.add(SlideModel(imageUrl = it))
        }
        binding.imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> finish()
        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityAdsDetailsBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}