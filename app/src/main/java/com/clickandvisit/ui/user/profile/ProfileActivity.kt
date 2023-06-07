package com.clickandvisit.ui.user.profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityProfileBinding
import com.clickandvisit.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileActivity : BaseActivity() {

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
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
    private fun registerBindingAndBaseObservers(binding: ActivityProfileBinding) {
        binding.viewModel = viewModel
        binding.picasso = getPicasso()
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}