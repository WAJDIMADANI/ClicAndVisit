package com.foodline.ui.home.news

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.foodline.R
import com.foodline.base.BaseActivity
import com.foodline.databinding.ActivityNewsBinding
import com.foodline.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsActivity : BaseActivity() {

    @Inject
    lateinit var newsListAdapter: NewsListAdapter

    private val viewModel: NewsViewModel by viewModels()

    private lateinit var binding : ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView<ActivityNewsBinding>(this, R.layout.activity_news)

        registerBindingAndBaseObservers(binding)
        registerNewsObservers()

    }


    /**
     * Register the UI for XML Binding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityNewsBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

    /**
     * register UI news Observers
     */
    private fun registerNewsObservers() {
        registerRecycler()
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> finish()
        }
    }

    private fun registerRecycler() {
        newsListAdapter.viewModel = viewModel
        binding.recyclerNews.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        binding.recyclerNews.adapter = newsListAdapter
    }


    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }
}
