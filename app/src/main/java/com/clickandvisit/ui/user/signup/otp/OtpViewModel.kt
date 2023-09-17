package com.clickandvisit.ui.user.signup.otp

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.user.signup.SignupResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.ExtraKeys
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
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) :
    BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {


    val one = MutableLiveData("")
    val two = MutableLiveData("")
    val three = MutableLiveData("")
    val four = MutableLiveData("")

    val code = MutableLiveData("")
    val userId = MutableLiveData(0)

    init {
        userId.value =
            savedStateHandle.getLiveData<Int>(ExtraKeys.OtpActivity.USER_ID_EXTRA_KEY).value

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
                    val otpResponse = withContext(schedulerProvider.dispatchersIO()) {
                        userRepository.activateAccount(code.value!!, userId.value!!)
                    }
                    onOtpValidateSuccess(otpResponse)
                }, { error ->
                    onOtpValidateError(error)
                })
            }
        }
    }

    @UiThread
    fun onSendActivationCodeClick() {
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.sendActivationCode(userId.value!!)
                }
                hideBlockProgressBar()
            }, { error ->
                hideBlockProgressBar()
            })
        }
    }

    private fun onOtpValidateSuccess(otpResponse: SignupResponse) {
        hideBlockProgressBar()
        if (otpResponse.resultCode == 4) {
            shownSimpleDialog(messageId = R.string.global_error_otp)
        } else if (otpResponse.user != null) {
            navigate(Navigation.HomeActivityNavigation)
        } else {
            shownServerErrorSimpleDialog()
        }
    }

    private fun onOtpValidateError(throwable: Throwable) {
        hideBlockProgressBar()
        one.value = ""
        two.value = ""
        three.value = ""
        four.value = ""
        code.value = ""
        //handleThrowable(throwable)
        shownSimpleDialog(messageId = R.string.global_error_otp)
    }

}
