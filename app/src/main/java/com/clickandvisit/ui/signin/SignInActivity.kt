package com.clickandvisit.ui.signin

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
import com.clickandvisit.global.utils.TAG
import com.clickandvisit.ui.home.HomeActivity
import com.clickandvisit.ui.home.HomeMainActivity
import com.clickandvisit.ui.password.ResetPasswordActivity
import com.clickandvisit.ui.signup.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity() {

    private val viewModel: SignInViewModel by viewModels()

    private lateinit var binding: ActivitySigninBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin)

        registerBindingAndBaseObservers(binding)
        registerSinInObservers()
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
                navigateToActivity(HomeMainActivity::class)
            }
            is Navigation.ResetPasswordActivityNavigation -> navigateToActivity(
                ResetPasswordActivity::class
            )
            is Navigation.SignUpActivityNavigation -> navigateToActivity(
                SignUpActivity::class,
                true
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