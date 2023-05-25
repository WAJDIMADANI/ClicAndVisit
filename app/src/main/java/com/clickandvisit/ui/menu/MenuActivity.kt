package com.clickandvisit.ui.menu

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityMenuBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.ui.home.HomeActivity
import com.clickandvisit.ui.menu.alerts.AlertsActivity
import com.clickandvisit.ui.menu.chat.ChatActivity
import com.clickandvisit.ui.menu.help.HelpActivity
import com.clickandvisit.ui.menu.myads.AdsActivity
import com.clickandvisit.ui.menu.mypackages.PackageActivity
import com.clickandvisit.ui.menu.payment.PaymentActivity
import com.clickandvisit.ui.menu.profile.ProfileActivity
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