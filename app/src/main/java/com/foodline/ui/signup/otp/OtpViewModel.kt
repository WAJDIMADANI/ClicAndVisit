package com.foodline.ui.signup.otp

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.foodline.base.BaseAndroidViewModel
import com.foodline.data.repository.abs.UserRepository
import com.foodline.global.helper.Navigation
import com.foodline.global.listener.SchedulerProvider
import com.foodline.global.listener.ToolBarListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OtpViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) :
    BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {


    val one = MutableLiveData("")
    val two = MutableLiveData("")
    val three = MutableLiveData("")
    val four = MutableLiveData("")

    val oneFieldError = MutableLiveData(false)
    val twoFieldError = MutableLiveData(false)
    val threeFieldError = MutableLiveData(false)
    val fourFieldError = MutableLiveData(false)


    init {

    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    fun onNextClick() {
    }
}
