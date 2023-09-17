package com.clickandvisit.ui.user.chat

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityChatBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.ui.user.chat.conv.ConvActivity
import com.clickandvisit.ui.user.signup.otp.OtpActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ChatActivity : BaseActivity() {

    private val viewModel: ChatViewModel by viewModels()

    private lateinit var binding: ActivityChatBinding

    @Inject
    lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        registerBindingAndBaseObservers(binding)
        registerRecycler(binding)
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.onRestart()
    }


    private fun registerRecycler(binding: ActivityChatBinding) {
        adapter.viewModel = viewModel
        binding.rvUsers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvUsers.adapter = adapter
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> finish()
            is Navigation.ConvActivityNavigation -> {
                Intent(this, ConvActivity::class.java).let {
                    it.putExtra(ExtraKeys.ConvActivity.DISC_ID_EXTRA_KEY, navigationTo.discId)
                    it.putExtra(ExtraKeys.ConvActivity.FROM_NAME_EXTRA_KEY, navigationTo.fromName)
                    startActivity(it)
                }
            }

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