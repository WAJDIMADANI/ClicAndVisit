package com.clickandvisit.ui.ads.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivitySearchBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.ExtraKeys
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var binding: ActivitySearchBinding

    @Inject
    lateinit var adapter: MySearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        registerBindingAndBaseObservers(binding)
        registerRecycler(binding)
    }

    private fun registerRecycler(binding: ActivitySearchBinding) {
        adapter.viewModel = viewModel
        binding.rvSearch.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvSearch.adapter = adapter
    }


    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {

            is Navigation.Back -> finish()

            is Navigation.HomeActivityNavigation -> onBackNavigation()
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
    private fun registerBindingAndBaseObservers(binding: ActivitySearchBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}