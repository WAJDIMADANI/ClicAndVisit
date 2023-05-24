package com.foodline.ui.home.send

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.foodline.R
import com.foodline.base.BaseActivity
import com.foodline.databinding.ActivitySendBinding
import com.foodline.global.helper.Navigation
import com.foodline.ui.home.send.city.CityFragment
import com.foodline.ui.home.send.date.DateFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendActivity : BaseActivity() {


    private val viewModel: SendViewModel by viewModels()

    private lateinit var binding: ActivitySendBinding

    var routineFragments: List<Fragment> = emptyList()

    var currentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send)
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
            CityFragment(),
            DateFragment()
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
        if (currentIndex == 1) {
            //TODO: navigate to ..
        } else {
            currentIndex++
            navigateToFragment()
        }
    }

    private fun navigateToFragment() {
        startFragment()
    }

    override fun onBackPressed() {
        onPreviousAction()
    }

    private fun onPreviousAction() {
        if (0 == currentIndex) {
            finish()
        } else {
            currentIndex--
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
    private fun registerBindingAndBaseObservers(binding: ActivitySendBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }
}