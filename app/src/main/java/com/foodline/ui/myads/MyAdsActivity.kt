package com.foodline.ui.myads

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.foodline.R
import com.foodline.base.BaseActivity
import com.foodline.databinding.ActivityMyAdsBinding
import com.foodline.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyAdsActivity : BaseActivity() {

    private val viewModel: MyAdsViewModel by viewModels()

    private lateinit var binding: ActivityMyAdsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_ads)

        registerBindingAndBaseObservers(binding)
        registerObservers()
    }


    /**
     * register UI Home activity Observers
     */
    private fun registerObservers() {
        //TODO
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        hideKeyboard()
        when (navigationTo) {
            is Navigation.Back -> finish()

        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityMyAdsBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}
