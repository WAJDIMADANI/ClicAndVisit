package com.foodline.ui.signup.otp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.foodline.R
import com.foodline.base.BaseActivity
import com.foodline.databinding.ActivityOtpBinding
import com.foodline.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpActivity : BaseActivity() {


    private val viewModel: OtpViewModel by viewModels()

    private lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp)

        registerBindingAndBaseObservers(binding)
    }


    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> finish()

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