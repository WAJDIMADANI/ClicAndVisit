package com.clickandvisit.ui.user.chat.conv

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityConvBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.observeOnlyNotNull
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ConvActivity : BaseActivity() {


    private val viewModel: ConvViewModel by viewModels()

    private lateinit var binding: ActivityConvBinding

    @Inject
    lateinit var adapter: ConvAdapter

    private var handler: Handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    private var delay = 10000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_conv)
        registerBindingAndBaseObservers(binding)
        registerRecycler(binding)


        viewModel.discId.observeOnlyNotNull(this) {
            handler.postDelayed(Runnable {
                handler.postDelayed(runnable!!, delay.toLong())
                viewModel.getMessages()
            }.also { runnable = it }, delay.toLong())
        }

    }


    private fun registerRecycler(binding: ActivityConvBinding) {
        adapter.viewModel = viewModel
        binding.rvUsers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvUsers.adapter = adapter
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
    private fun registerBindingAndBaseObservers(binding: ActivityConvBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}