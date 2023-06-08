package com.clickandvisit.ui.user.signup

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.user.User
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.HttpResponseCode
import com.clickandvisit.global.utils.isValidEmail
import com.clickandvisit.global.utils.isWhiteSpaces
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {


    val email: MutableLiveData<String> = MutableLiveData()
    val phone: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val emailFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val phoneFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val passwordFieldError: MutableLiveData<Boolean> = MutableLiveData(false)


    /** Radio buttons states  (pro or not)**/
    val checkedPro: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedNoPro: MutableLiveData<Boolean> = MutableLiveData(true)


    /** no pro **/
    val checkedM: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedF: MutableLiveData<Boolean> = MutableLiveData(false)

    val userName: MutableLiveData<String> = MutableLiveData()
    val firstname: MutableLiveData<String> = MutableLiveData()

    val userFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val firstnameFieldError: MutableLiveData<Boolean> = MutableLiveData(false)


    /** pro  **/
    val social: MutableLiveData<String> = MutableLiveData()
    val siret: MutableLiveData<String> = MutableLiveData()

    val socialFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val siretFieldError: MutableLiveData<Boolean> = MutableLiveData(false)


    init {

    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    /** Radio Buttons  **/

    fun onProClick() {
        checkedPro.value = true
        checkedNoPro.value = false
    }

    fun onNoProClick() {
        checkedPro.value = false
        checkedNoPro.value = true
    }

    fun onMClick() {
        checkedM.value = true
        checkedF.value = false
    }

    fun onFClick() {
        checkedM.value = false
        checkedF.value = true
    }


    @UiThread
    fun onSignUpClicked() {
        navigate(Navigation.OtpActivityNavigation)
        if (validateFields()) {

/*            viewModelScope.launch {
                showBlockProgressBar()
                tryCatch({

                }, { error ->
                    onSignInError(error)
                })
            }*/
        }
    }


    private fun onSignUpSuccess(userResponse: User) {
        hideBlockProgressBar()
        navigate(Navigation.HomeActivityNavigation)
    }


    private fun onSignInError(throwable: Throwable) {
        hideBlockProgressBar()
        if (throwable is HttpException) {
            when (throwable.code()) {
                HttpResponseCode.HTTP_PAYMENT_REQUIRED -> shownSimpleDialog(messageId = R.string.global_error_banned_user)
                else -> handleThrowable(throwable)
            }
        } else {
            handleThrowable(throwable)
        }
    }

    /** Fields validation  **/

    private fun validateFields(): Boolean {
        return if (checkedPro.value == true) {
            validUserName() and validFirstName() and validEmail() and validPhone() and validPassword()
        } else {
            validUserName() and validFirstName() and validEmail() and validPhone() and validPassword()
        }
    }


    private fun validUserName() = if (userName.value.isWhiteSpaces()) {
        userFieldError.value = true
        false
    } else {
        true
    }

    private fun validFirstName() = if (firstname.value.isWhiteSpaces()) {
        firstnameFieldError.value = true
        false
    } else {
        true
    }

    private fun validEmail() = if (email.value.isWhiteSpaces() || !email.value.isValidEmail()) {
        emailFieldError.value = true
        false
    } else {
        true
    }

    private fun validPhone() = if (phone.value.isWhiteSpaces()) {
        phoneFieldError.value = true
        false
    } else {
        true
    }

    private fun validPassword() = if (password.value.isWhiteSpaces()) {
        passwordFieldError.value = true
        false
    } else {
        true
    }


}
