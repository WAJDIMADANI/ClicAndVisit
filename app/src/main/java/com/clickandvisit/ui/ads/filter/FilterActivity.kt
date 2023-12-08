package com.clickandvisit.ui.ads.filter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityFilterBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.ExtraKeys
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FilterActivity : BaseActivity() {

    private val viewModel: FilterViewModel by viewModels()

    private lateinit var binding: ActivityFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        registerBindingAndBaseObservers(binding)
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> {
                val intent = Intent()
                setResult(Activity.RESULT_CANCELED, intent)
                finish()
            }
            is Navigation.HomeActivityNavigationData -> {
                val intent = Intent()
                 intent.putExtra(
                     ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY,
                     navigationTo.searchRequest
                 )
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

        }
    }

    private fun onBackNavigation() {
        val intent = Intent()
        intent.putExtra(ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY, viewModel.searchRequest.value)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityFilterBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}