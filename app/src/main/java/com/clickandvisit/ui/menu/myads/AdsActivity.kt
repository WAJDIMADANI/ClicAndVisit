package com.clickandvisit.ui.menu.myads

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityAdsBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.ui.menu.details.DetailsActivity
import com.clickandvisit.ui.menu.filter.FilterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdsActivity : BaseActivity() {


    private val viewModel: AdsViewModel by viewModels()

    private lateinit var binding: ActivityAdsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_ads)

        registerBindingAndBaseObservers(binding)
    }


    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> finish()
            is Navigation.FilterActivityNavigation -> navigateToActivity(FilterActivity::class)
            is Navigation.DetailsActivityNavigation -> {
                Intent(this, DetailsActivity::class.java).let {
                    it.putExtra(ExtraKeys.DetailsActivity.DETAILS_EXTRA_KEY_FROM_ADS, true)
                    it.putExtra(ExtraKeys.DetailsActivity.DETAILS_EXTRA_KEY_FROM_PACK, false)
                    startActivity(it)
                }
            }
        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityAdsBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}