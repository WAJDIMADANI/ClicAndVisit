package com.clickandvisit.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityHomeBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.ui.home.send.SendActivity
import com.clickandvisit.ui.home.transport.TransportActivity
import com.clickandvisit.ui.menu.MenuActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        registerBindingAndBaseObservers(binding)
        registerHomeObservers()
    }


    /**
     * register UI Home activity Observers
     */
    private fun registerHomeObservers() {
        //TODO
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        hideKeyboard()
        when (navigationTo) {
            is Navigation.Back -> finish()
            is Navigation.MenuActivityNavigation -> navigateToActivity(MenuActivity::class)
            is Navigation.TransportActivityNavigation -> navigateToActivity(TransportActivity::class)
            is Navigation.SendActivityNavigation -> navigateToActivity(SendActivity::class)
            else -> {}
        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityHomeBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}
