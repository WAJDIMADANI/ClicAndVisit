package com.foodline.ui.menu

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.foodline.R
import com.foodline.base.BaseActivity
import com.foodline.databinding.ActivityMenuBinding
import com.foodline.global.helper.Navigation
import com.foodline.ui.home.HomeActivity
import com.foodline.ui.menu.alerts.AlertsActivity
import com.foodline.ui.menu.chat.ChatActivity
import com.foodline.ui.menu.help.HelpActivity
import com.foodline.ui.menu.myads.AdsActivity
import com.foodline.ui.menu.mypackages.PackageActivity
import com.foodline.ui.menu.payment.PaymentActivity
import com.foodline.ui.menu.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : BaseActivity() {

    private val viewModel: MenuViewModel by viewModels()

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu)

        registerBindingAndBaseObservers(binding)
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> finish()

            is Navigation.HomeActivityNavigation -> {
                navigateToActivity(HomeActivity::class)
            }

            is Navigation.HelpActivityNavigation -> {
                navigateToActivity(HelpActivity::class)
            }

            is Navigation.ProfileActivityNavigation -> {
                navigateToActivity(ProfileActivity::class)
            }

            is Navigation.AlertsActivityNavigation -> {
                navigateToActivity(AlertsActivity::class)
            }

            is Navigation.PaymentActivityNavigation -> {
                navigateToActivity(PaymentActivity::class)
            }

            is Navigation.ChatActivityNavigation -> {
                navigateToActivity(ChatActivity::class)
            }

            is Navigation.AdsActivityNavigation -> {
                navigateToActivity(AdsActivity::class)
            }

            is Navigation.PackageActivityNavigation -> {
                navigateToActivity(PackageActivity::class)
            }

        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityMenuBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}