package com.foodline.ui.menu.help

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.foodline.R
import com.foodline.base.BaseActivity
import com.foodline.databinding.ActivityHelpBinding
import com.foodline.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HelpActivity : BaseActivity() {

    private val viewModel: HelpViewModel by viewModels()

    private lateinit var binding: ActivityHelpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_help)


        registerBindingAndBaseObservers(binding)
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
    private fun registerBindingAndBaseObservers(binding: ActivityHelpBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }


}