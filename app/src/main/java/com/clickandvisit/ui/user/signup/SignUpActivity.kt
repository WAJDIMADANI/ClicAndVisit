package com.clickandvisit.ui.user.signup

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivitySignupBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.ui.user.signin.SignInActivity
import com.clickandvisit.ui.user.signup.otp.OtpActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpActivity : BaseActivity() {

    private val viewModel: SignUpViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivitySignupBinding>(this, R.layout.activity_signup)

        registerBindingAndBaseObservers(binding)
        registerSinInObservers()
    }

    private fun registerSinInObservers() {
        //TODO
    }


    /** Pro RadioButton click **/
    fun onProClick(view: View) {
        viewModel.onProClick()
    }
    /** No pro RadioButton click **/
    fun onNoProClick(view: View) {
        viewModel.onNoProClick()
    }

    /** Pro RadioButton click **/
    fun onMClick(view: View) {
        viewModel.onMClick()
    }
    /** No pro RadioButton click **/
    fun onFClick(view: View) {
        viewModel.onFClick()
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> finish()

            is Navigation.SignInActivityNavigation -> navigateToActivity(
                SignInActivity::class,
                true
            )

            is Navigation.OtpActivityNavigation -> {
                navigateToActivity(OtpActivity::class, true)
            }

        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivitySignupBinding) {
        binding.viewModel = viewModel
        binding.picasso = getPicasso()
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }
}
