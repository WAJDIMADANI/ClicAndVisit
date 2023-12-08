package com.clickandvisit.ui.user.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.data.model.user.User
import com.clickandvisit.databinding.ActivitySigninBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.ui.home.HomeActivity
import com.clickandvisit.ui.user.password.WebViewActivity
import com.clickandvisit.ui.user.signup.SignUpActivity
import com.clickandvisit.ui.user.signup.otp.OtpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity() {

    private val viewModel: SignInViewModel by viewModels()

    private lateinit var binding: ActivitySigninBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin)

        registerBindingAndBaseObservers(binding)
        registerSinInObservers()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    private fun registerSinInObservers() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val RC_SIGN_IN = 0
        const val TAG_KOTLIN = "TAG_KOTLIN"
    }


    /**
     * handling navigation event
     */
    private fun navigateToHome(user: User) {
        Intent(this, HomeActivity::class.java).let {
            it.putExtra(ExtraKeys.HomeActivity.HOME_EXTRA_USER_KEY, user)
            startActivity(it)
            finish()
        }
    }


    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.HomeActivityNavigation -> {
                navigateToActivity(HomeActivity::class, true)
            }
            is Navigation.OtpActivityNavigation -> {
                Intent(this, OtpActivity::class.java).let {
                    it.putExtra(ExtraKeys.OtpActivity.USER_ID_EXTRA_KEY, navigationTo.userId)
                    startActivity(it)
                }
                finish()
            }
            is Navigation.ResetPasswordActivityNavigation -> navigateToActivity(
                WebViewActivity::class
            )
            is Navigation.SignUpActivityNavigation -> navigateToActivity(
                SignUpActivity::class
            )
            is Navigation.Back -> finish()
            else -> {}
        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivitySigninBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }
}

fun Any.print() {
    DebugLog.v(SignInActivity.TAG_KOTLIN, " $this")
}