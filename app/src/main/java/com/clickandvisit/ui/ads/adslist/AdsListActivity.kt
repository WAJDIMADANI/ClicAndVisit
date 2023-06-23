package com.clickandvisit.ui.ads.adslist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityAdsListBinding
import com.clickandvisit.databinding.ActivityHomeBinding
import com.clickandvisit.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AdsListActivity : BaseActivity() {

    private val viewModel: AdsListViewModel by viewModels()

    private lateinit var binding: ActivityAdsListBinding

    @Inject
    lateinit var adapter: PropertyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ads_list)
        registerBindingAndBaseObservers(binding)
        registerRecycler(binding)
    }

    private fun registerRecycler(binding: ActivityAdsListBinding) {
        adapter.viewModel = viewModel
        binding.rvAds.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvAds.adapter = adapter
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
    private fun registerBindingAndBaseObservers(binding: ActivityAdsListBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}