package com.clickandvisit.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.data.model.user.User
import com.clickandvisit.databinding.ActivitySplashBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.global.utils.TAG
import com.clickandvisit.ui.home.HomeActivity
import com.clickandvisit.ui.intro.IntroActivity
import com.clickandvisit.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        registerBindingAndBaseObservers(binding)
        registerLoggedInObservers()
    }


    private fun registerLoggedInObservers() {
        //TODO
    }


    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.HomeActivityNavigation -> {
                navigateToActivity(HomeActivity::class)
            }
            is Navigation.SignInActivityNavigation -> {
                DebugLog.d(TAG, "User is loggedOut")
                navigateToActivity(SignInActivity::class, true)
            }
            is Navigation.IntroActivityNavigation -> {
                navigateToActivity(IntroActivity::class, true)
            }
        }
    }


    private fun navigateToHome(user: User) {
        Intent(this, HomeActivity::class.java).let {
            it.putExtra(ExtraKeys.HomeActivity.HOME_EXTRA_USER_KEY, user)
            startActivity(it)
            finish()
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
