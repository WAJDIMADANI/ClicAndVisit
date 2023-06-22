package com.clickandvisit.ui.ads.addads

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.data.model.property.add.PropertyAdd
import com.clickandvisit.databinding.ActivityAddAdsBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.ui.ads.addads.five.CalendarFragment
import com.clickandvisit.ui.ads.addads.stepfour.FourFragment
import com.clickandvisit.ui.ads.addads.stepone.OneFragment
import com.clickandvisit.ui.ads.addads.stepthree.ThreeFragment
import com.clickandvisit.ui.ads.addads.steptwo.TwoFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddAdsActivity : BaseActivity() {

    private val viewModel: AddAdsViewModel by viewModels()

    lateinit var binding: ActivityAddAdsBinding

    private var routineFragments: List<Fragment> = emptyList()

    var currentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_ads)
        registerBindingAndBaseObservers(binding)
        registerHomeObservers()
        startFragment()
    }


    /**
     * register UI Home activity Observers
     */
    private fun registerHomeObservers() {
        initData(viewModel.propertyAdd.value)
        initListener()
    }

    private fun initData(value: PropertyAdd?) {
        routineFragments = listOf(
            OneFragment(value),
            TwoFragment(value),
            ThreeFragment(value),
            FourFragment(value),
            CalendarFragment(value)
        )
    }

    private fun initListener() {
        //&& (routineFragments[0] as OneFragment).viewModel.validateFields()
        binding.cbNext.setOnClickListener {
            onNextAction()
        }
        binding.cbBack.setOnClickListener {
            onPreviousAction()
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
        when (currentIndex) {

            4 -> {
                // CalendarFragment interface
                // add or edit reservation
            }

            3 -> {

                viewModel.mainPhotoUri.value = (routineFragments[3] as FourFragment).viewModel.mainPhotoUri.value


                currentIndex++
                // FIXME: call after ws success -> navigateToFragment()
                viewModel.createOrUpdateProperty()
            }
            2 -> {

                viewModel.city.value = (routineFragments[2] as ThreeFragment).viewModel.city.value
                viewModel.otherInfo.value = (routineFragments[2] as ThreeFragment).viewModel.otherInfo.value

                currentIndex++
                navigateToFragment()
            }
            1 -> {

                viewModel.roomNbrApi1.value = (routineFragments[1] as TwoFragment).viewModel.roomNbrApi1.value


                currentIndex++
                navigateToFragment()
            }
            0 -> {
                viewModel.checkedSale.value = (routineFragments[0] as OneFragment).viewModel.checkedSale.value

                currentIndex++
                navigateToFragment()
            }
        }
    }


    private fun onPreviousAction() {
        if (0 == currentIndex) {
            finish()
        } else {
            currentIndex--
            when (currentIndex) {
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
            startFragment()
        }
    }


    private fun navigateToFragment() {
        startFragment()
        when (currentIndex) {
            1 -> {
                stepOne()
            }
            2 -> {
                stepTwo()
            }
            3 -> {
                stepThree()
            }
            4 -> {
                stepFour()
            }
            5 -> {
                stepFive()
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
        binding.tvStep2.visibility = View.INVISIBLE
    }

    fun stepTwo() {
        binding.tvStep2.visibility = View.VISIBLE
        binding.tvStep3.visibility = View.INVISIBLE
    }

    fun stepThree() {
        binding.tvStep3.visibility = View.VISIBLE
        binding.tvStep4.visibility = View.INVISIBLE
    }

    fun stepFour() {
        binding.tvStep4.visibility = View.VISIBLE
        binding.tvStep5.visibility = View.INVISIBLE
    }

    fun stepFive() {
        binding.tvStep5.visibility = View.VISIBLE
    }

}