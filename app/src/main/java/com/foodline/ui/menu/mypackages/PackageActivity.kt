package com.foodline.ui.menu.mypackages

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.foodline.R
import com.foodline.base.BaseActivity
import com.foodline.databinding.ActivityPackageBinding
import com.foodline.global.helper.Navigation
import com.foodline.global.utils.ExtraKeys
import com.foodline.ui.menu.details.DetailsActivity
import com.foodline.ui.menu.filter.FilterActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PackageActivity : BaseActivity() {


    private val viewModel: PackageViewModel by viewModels()

    private lateinit var binding: ActivityPackageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_package)

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
                    it.putExtra(ExtraKeys.DetailsActivity.DETAILS_EXTRA_KEY_FROM_PACK, true)
                    it.putExtra(ExtraKeys.DetailsActivity.DETAILS_EXTRA_KEY_FROM_ADS, false)
                    startActivity(it)
                }
            }
        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityPackageBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}