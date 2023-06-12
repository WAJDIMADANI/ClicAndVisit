package com.clickandvisit.ui.user.signup.otp

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.user.signup.SignupResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    val code = MutableLiveData("")

    init {

    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    @UiThread
    fun onNextClick() {
        code.value = one.value + two.value + three.value + four.value
        if (code.value!!.length == 4) {
            showBlockProgressBar()
            viewModelScope.launch {
                tryCatch({
                    val loginResponse = withContext(schedulerProvider.dispatchersIO()) {
                        userRepository.activateAccount(code.value!!)
                    }
                    onOtpValidateSuccess(loginResponse)
                }, { error ->
                    onOtpValidateError(error)
                })
            }
        }
    }

    private fun onOtpValidateSuccess(signupResponse: SignupResponse) {
        hideBlockProgressBar()
        navigate(Navigation.HomeActivityNavigation)
    }

    private fun onOtpValidateError(throwable: Throwable) {
        hideBlockProgressBar()
        one.value = ""
        two.value = ""
        three.value = ""
        four.value = ""
        code.value = ""
        handleThrowable(throwable)
    }

}
