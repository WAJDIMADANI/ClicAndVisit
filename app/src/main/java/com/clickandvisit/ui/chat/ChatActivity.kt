package com.clickandvisit.ui.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityChatBinding
import com.clickandvisit.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChatActivity : BaseActivity() {

    private val viewModel: ChatViewModel by viewModels()

    private lateinit var binding: ActivityChatBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
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
    private fun registerBindingAndBaseObservers(binding: ActivityChatBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}