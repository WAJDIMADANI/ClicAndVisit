package com.clickandvisit.ui.home.transport

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityTransportBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.ui.home.transport.conf.ConfFragment
import com.clickandvisit.ui.home.transport.info.InfoFragment
import com.clickandvisit.ui.home.transport.pack.PackFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransportActivity : BaseActivity() {

    private val viewModel: TransportViewModel by viewModels()

    private lateinit var binding: ActivityTransportBinding

    var routineFragments: List<Fragment> = emptyList()

    var currentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transport)
        registerBindingAndBaseObservers(binding)
        registerHomeObservers()
        startFragment()
    }

    /**
     * register UI Home activity Observers
     */
    private fun registerHomeObservers() {
        initData()
        initListener()
    }


    private fun initData() {
        routineFragments = listOf(
            InfoFragment(),
            PackFragment(),
            ConfFragment()
        )
    }

    private fun initListener() {
        binding.cbNext.setOnClickListener {
            onNextAction()
        }
    }

    private fun startFragment() {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.flTransport, routineFragments[currentIndex])
            }
            .also {
                it.disallowAddToBackStack()
            }
            .run {
                commit()
            }
    }


    private fun onNextAction() {
        if (currentIndex == 2) {
            //TODO: navigate to ..
        } else {
            currentIndex++
            navigateToFragment()
        }
    }

    private fun navigateToFragment() {
        startFragment()
        if (currentIndex == 1) {
            binding.llStepTwoBackground.background = getDrawable(R.drawable.ic_button_background)
            binding.ivStepTwoBackground.background = getDrawable(R.drawable.ic_box_off)
        } else if (currentIndex == 2) {
            binding.cbNext.text = getString(R.string.ads_publish)
            binding.llStepThreeBackground.background = getDrawable(R.drawable.ic_button_background)
            binding.ivStepThreeBackground.background = getDrawable(R.drawable.ic_done_white)
        }
    }

    override fun onBackPressed() {
        onPreviousAction()
    }

    private fun onPreviousAction() {
        if (0 == currentIndex) {
            finish()
        } else {
            currentIndex--
            if (currentIndex == 1) {
                binding.llStepThreeBackground.background = getDrawable(R.drawable.ic_gray_background)
                binding.ivStepThreeBackground.background = getDrawable(R.drawable.ic_done)
            } else if (currentIndex == 0) {
                binding.llStepTwoBackground.background = getDrawable(R.drawable.ic_gray_background)
                binding.ivStepTwoBackground.background = getDrawable(R.drawable.ic_box_gray)
            }
            startFragment()
        }
    }


    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        hideKeyboard()
        when (navigationTo) {
            is Navigation.Back -> onBackPressed()

        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityTransportBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}