package com.clickandvisit.ui.ads.addads

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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

    var routineFragments: List<Fragment> = emptyList()


    var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_ads)
        registerBindingAndBaseObservers(binding)
        binding.vpIntro.adapter = AddAdsPagerAdapter(this, binding, applicationContext)
        binding.vpIntro.isUserInputEnabled = false
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
                    4 -> {
                        stepFive()
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
            when (currentIndex) {
                0 -> {
                    binding.vpIntro.currentItem = 1
                }
                1 -> {
                    binding.vpIntro.currentItem = 2
                }
                2 -> {
                    binding.vpIntro.currentItem = 3
                }
                3 -> {
                    viewModel.createOrUpdateProperty()
                    //binding.vpIntro.currentItem = 4
                }
            }
        }

        binding.cbBack.setOnClickListener {
            when (currentIndex) {
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
                4 -> {
                    binding.vpIntro.currentItem = 3
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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
        currentIndex = 0
        binding.tvStep2.visibility = View.INVISIBLE
    }

    fun stepTwo() {
        currentIndex = 1
        binding.tvStep2.visibility = View.VISIBLE
        binding.tvStep3.visibility = View.INVISIBLE
    }

    fun stepThree() {
        currentIndex = 2
        binding.tvStep3.visibility = View.VISIBLE
        binding.tvStep4.visibility = View.INVISIBLE
    }

    fun stepFour() {
        currentIndex = 3
        binding.tvStep4.visibility = View.VISIBLE
        binding.tvStep5.visibility = View.INVISIBLE
    }

    fun stepFive() {
        currentIndex = 4
        binding.tvStep5.visibility = View.VISIBLE
    }

}