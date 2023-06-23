package com.clickandvisit.ui.ads.addads

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.data.model.property.Property
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
        initData(viewModel.propertyEdit.value)
        initListener()
    }

    private fun initData(value: Property?) {
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

                viewModel.mainPhotoUri.value =
                    (routineFragments[3] as FourFragment).viewModel.mainPhotoUri.value

                currentIndex++
                viewModel.createOrUpdateProperty()
            }
            2 -> {
                if ((routineFragments[2] as ThreeFragment).viewModel.validateFields()) {
                    viewModel.city.value = (routineFragments[2] as ThreeFragment).viewModel.city.value
                    viewModel.postalCode.value =
                        (routineFragments[2] as ThreeFragment).viewModel.postalCode.value
                    viewModel.address.value =
                        (routineFragments[2] as ThreeFragment).viewModel.address.value
                    viewModel.inter.value = (routineFragments[2] as ThreeFragment).viewModel.inter.value
                    viewModel.interCode.value =
                        (routineFragments[2] as ThreeFragment).viewModel.interCode.value
                    viewModel.otherInfo.value =
                        (routineFragments[2] as ThreeFragment).viewModel.otherInfo.value

                    currentIndex++
                    navigateToFragment()
                }

            }
            1 -> {

                viewModel.roomNbrApi1.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi1.value
                viewModel.roomNbrApi2.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi2.value
                viewModel.roomNbrApi3.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi3.value
                viewModel.roomNbrApi4.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi4.value
                viewModel.roomNbrApi5.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi5.value
                viewModel.roomNbrApi6.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi6.value
                viewModel.roomNbrApi7.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi7.value
                viewModel.roomNbrApi8.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi8.value
                viewModel.roomNbrApi9.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi9.value
                viewModel.roomNbrApi10.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi10.value

                viewModel.roomNbrApi12.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi12.value
                viewModel.roomNbrApi13.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi13.value
                viewModel.roomNbrApi14.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi14.value
                viewModel.roomNbrApi15.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi5.value
                viewModel.roomNbrApi16.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi16.value
                viewModel.roomNbrApi17.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi17.value
                viewModel.roomNbrApi18.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi18.value
                viewModel.roomNbrApi19.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi19.value
                viewModel.roomNbrApi20.value =
                    (routineFragments[1] as TwoFragment).viewModel.roomNbrApi20.value


                currentIndex++
                navigateToFragment()
            }
            0 -> {
                if ((routineFragments[0] as OneFragment).viewModel.validateFields()) {
                    viewModel.checkedSale.value =
                        (routineFragments[0] as OneFragment).viewModel.checkedSale.value
                    viewModel.checkedRent.value =
                        (routineFragments[0] as OneFragment).viewModel.checkedRent.value
                    viewModel.checkedHome.value =
                        (routineFragments[0] as OneFragment).viewModel.checkedHome.value
                    viewModel.checkedB.value =
                        (routineFragments[0] as OneFragment).viewModel.checkedB.value
                    viewModel.checkedApp.value =
                        (routineFragments[0] as OneFragment).viewModel.checkedApp.value
                    viewModel.checkedTer.value =
                        (routineFragments[0] as OneFragment).viewModel.checkedTer.value
                    viewModel.checkedGarage.value =
                        (routineFragments[0] as OneFragment).viewModel.checkedGarage.value
                    viewModel.checkedComm.value =
                        (routineFragments[0] as OneFragment).viewModel.checkedComm.value

                    viewModel.checked1.value =
                        (routineFragments[0] as OneFragment).viewModel.checked1.value
                    viewModel.checked2.value =
                        (routineFragments[0] as OneFragment).viewModel.checked2.value
                    viewModel.checked3.value =
                        (routineFragments[0] as OneFragment).viewModel.checked3.value
                    viewModel.checked4.value =
                        (routineFragments[0] as OneFragment).viewModel.checked4.value
                    viewModel.checked5.value =
                        (routineFragments[0] as OneFragment).viewModel.checked5.value
                    viewModel.checkedNA.value =
                        (routineFragments[0] as OneFragment).viewModel.checkedNA.value

                    viewModel.surface.value =
                        (routineFragments[0] as OneFragment).viewModel.surface.value
                    viewModel.price.value =
                        (routineFragments[0] as OneFragment).viewModel.price.value
                    viewModel.stage.value =
                        (routineFragments[0] as OneFragment).viewModel.stage.value
                    viewModel.on.value = (routineFragments[0] as OneFragment).viewModel.on.value
                    viewModel.info.value = (routineFragments[0] as OneFragment).viewModel.info.value

                    currentIndex++
                    navigateToFragment()
                }
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
        when (navigationTo) {

            is Navigation.Back -> finish()

            is Navigation.CalendarFragmentNavigation -> {
                navigateToFragment()
            }

        }
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