package com.clickandvisit.ui.user.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivitySplashBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.ui.ads.adsdetails.AdsDetailsActivity
import com.clickandvisit.ui.home.HomeActivity
import com.clickandvisit.ui.user.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        registerBindingAndBaseObservers(binding)

    }

    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.HomeActivityNavigation -> {
                navigateToActivity(HomeActivity::class, true)
            }
            is Navigation.HomeActivityNav -> {
                Intent(this, HomeActivity::class.java).let { intent ->
                    intent.putExtra(
                        ExtraKeys.HomeNotificationKeys.HOME_NOTIFICATION_EXTRA_KEY,
                        navigationTo.key
                    )
                    startActivity(intent)
                }
            }
            is Navigation.SignInActivityNavigation -> {
                navigateToActivity(SignInActivity::class, true)
            }
        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observer
     */
    private fun registerBindingAndBaseObservers(binding: ActivitySplashBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }
}
