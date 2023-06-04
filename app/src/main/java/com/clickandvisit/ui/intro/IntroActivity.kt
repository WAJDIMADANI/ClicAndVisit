package com.clickandvisit.ui.intro

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityIntroBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.ui.shared.adapter.IntroViewPagerAdapter
import com.clickandvisit.ui.signin.SignInActivity
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
                    3 -> {
                        stepFour()
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
                2 -> {
                    binding.vpIntro.currentItem = 3
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
        binding.ivStep.background = getDrawable(R.drawable.ic_add_step_1)
    }

    fun stepTwo() {
        stepNumber = 1
        binding.ivStep.background = getDrawable(R.drawable.ic_add_step_2)
    }

    fun stepThree() {
        stepNumber = 2
        binding.ivStep.background = getDrawable(R.drawable.ic_add_step_3)
    }

    fun stepFour() {
        stepNumber = 3
        binding.ivStep.background = getDrawable(R.drawable.ic_add_step_4)
    }

}