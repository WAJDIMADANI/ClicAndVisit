package com.clickandvisit.ui.ads.addads

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityAddAdsBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.ui.shared.adapter.AddAdsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddAdsActivity : BaseActivity() {

    private val viewModel: AddAdsViewModel by viewModels()

    lateinit var binding: ActivityAddAdsBinding

    var stepNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_ads)
        registerBindingAndBaseObservers(binding)
        binding.vpIntro.adapter = AddAdsPagerAdapter(this, binding, applicationContext)

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
        binding.cbNext.setOnClickListener {
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
                /*3 -> {
                    binding.vpIntro.currentItem = 4
                }*/
            }
        }

        binding.cbBack.setOnClickListener {
            when (stepNumber) {
                0 -> {
                    finish()
                }
                1 -> {
                    binding.vpIntro.currentItem = 0
                }
                2 -> {
                    binding.vpIntro.currentItem = 1
                }
                3 -> {
                    binding.vpIntro.currentItem = 2
                }
            }
        }
    }

    override fun navigate(navigationTo: Navigation) {

    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observer
     */
    private fun registerBindingAndBaseObservers(binding: ActivityAddAdsBinding) {
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