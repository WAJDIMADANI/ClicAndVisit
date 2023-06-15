package com.clickandvisit.ui.user.signup.otp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityOtpBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpActivity : BaseActivity() {


    private val viewModel: OtpViewModel by viewModels()

    private lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp)

        reqFocus(binding.otpEditTextOne, binding.otpEditTextTwo)
        reqFocus(binding.otpEditTextTwo, binding.otpEditTextThree)
        reqFocus(binding.otpEditTextThree, binding.otpEditTextFour)

        registerBindingAndBaseObservers(binding)
    }

    private fun reqFocus(current: EditText, next: EditText) {
        current.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                next.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }


    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> finish()

            is Navigation.HomeActivityNavigation -> {
                navigateToActivity(HomeActivity::class, true)
            }

        }
    }


    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityOtpBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }
}