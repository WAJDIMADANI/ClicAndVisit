package com.foodline.ui.intro

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.foodline.R
import com.foodline.base.BaseActivity
import com.foodline.databinding.ActivityIntroBinding
import com.foodline.global.helper.Navigation
import com.foodline.ui.shared.adapter.IntroViewPagerAdapter
import com.foodline.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


@AndroidEntryPoint
class IntroActivity : BaseActivity() {

    private val viewModel: IntroViewModel by viewModels()

    lateinit var binding: ActivityIntroBinding

    var stepNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)
        registerBindingAndBaseObservers(binding)
        binding.vpIntro.adapter = IntroViewPagerAdapter(this, binding, applicationContext)

        TabLayoutMediator(binding.mTabLayout, binding.vpIntro) { tab, position ->

        }.attach()

        binding.mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        stepOne()
                    }
                    1 -> {
                        stepTwo()
                    }
                    2 -> {
                        stepThree()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        stepOne()
        onButtonsClick()
    }

    private fun onButtonsClick() {
        binding.cbIntroNext.setOnClickListener {
            when (stepNumber) {
                0 -> {
                    binding.vpIntro.currentItem = 1
                }
                1 -> {
                    binding.vpIntro.currentItem = 2
                }
            }
        }

        binding.cbIntroFinish.setOnClickListener {
            navigateToActivity(SignInActivity::class, true)
        }
    }

    override fun navigate(navigationTo: Navigation) {

    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observer
     */
    private fun registerBindingAndBaseObservers(binding: ActivityIntroBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

    fun stepOne() {
        stepNumber = 0
        binding.llStepContainer.visibility = View.VISIBLE
        binding.ivStepOne.visibility = View.GONE
        binding.ivStepTwo.visibility = View.VISIBLE
    }

    fun stepTwo() {
        stepNumber = 1
        binding.cbIntroNext.text = getString(R.string.global_next)
        binding.cbIntroNext.visibility = View.VISIBLE
        binding.llStepContainer.visibility = View.VISIBLE
        binding.ivStepOne.visibility = View.VISIBLE
        binding.ivStepTwo.visibility = View.GONE
        binding.cbIntroFinish.visibility = View.GONE
    }

    fun stepThree() {
        stepNumber = 2
        binding.llStepContainer.visibility = View.GONE
        binding.cbIntroFinish.visibility = View.VISIBLE
        binding.cbIntroNext.visibility = View.GONE
    }

}