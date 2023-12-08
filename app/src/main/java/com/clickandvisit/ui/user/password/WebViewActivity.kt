package com.clickandvisit.ui.user.password

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.databinding.ActivityResetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.global.helper.Navigation

@AndroidEntryPoint
class WebViewActivity : BaseActivity() {

    private val viewModel: WebViewViewModel by viewModels()

    private lateinit var binding: ActivityResetPasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password)

        registerBindingAndBaseObservers(binding)
        initWebView(binding)

    }

    private fun initWebView(binding: ActivityResetPasswordBinding) {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> finish()
            else -> {}
        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityResetPasswordBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}