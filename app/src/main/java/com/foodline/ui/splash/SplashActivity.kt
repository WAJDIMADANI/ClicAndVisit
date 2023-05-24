package com.foodline.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.foodline.R
import com.foodline.base.BaseActivity
import com.foodline.data.model.user.User
import com.foodline.databinding.ActivitySplashBinding
import com.foodline.global.helper.Navigation
import com.foodline.global.utils.DebugLog
import com.foodline.global.utils.ExtraKeys
import com.foodline.global.utils.TAG
import com.foodline.ui.home.HomeActivity
import com.foodline.ui.intro.IntroActivity
import com.foodline.ui.signin.SignInActivity
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
