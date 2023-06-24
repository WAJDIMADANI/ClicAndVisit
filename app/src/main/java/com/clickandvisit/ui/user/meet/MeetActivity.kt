package com.clickandvisit.ui.user.meet

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityMeetBinding
import com.clickandvisit.global.helper.Navigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MeetActivity : BaseActivity() {

    private val viewModel: MeetViewModel by viewModels()

    private lateinit var binding: ActivityMeetBinding

    @Inject
    lateinit var adapter: MeetAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meet)
        registerBindingAndBaseObservers(binding)
        registerRecycler(binding)
    }

    private fun registerRecycler(binding: ActivityMeetBinding) {
        adapter.viewModel = viewModel
        binding.rvMeet.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvMeet.adapter = adapter
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
    private fun registerBindingAndBaseObservers(binding: ActivityMeetBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}