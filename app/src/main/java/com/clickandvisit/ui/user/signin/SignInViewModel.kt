package com.clickandvisit.ui.user.signin

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.user.signup.SignupResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.HttpResponseCode
import com.clickandvisit.global.utils.hideKeyboard
import com.clickandvisit.global.utils.isValidEmail
import com.clickandvisit.global.utils.isWhiteSpaces
import com.clickandvisit.global.utils.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class SignInViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) :
    BaseAndroidViewModel(application, schedulerProvider) {


    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val emailFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val passwordFieldError: MutableLiveData<Boolean> = MutableLiveData(false)

    init {

    }

    fun onSignUpClicked() {
        navigate(Navigation.SignUpActivityNavigation)
    }

    fun onForgetPasswordClicked() {
        navigate(Navigation.ResetPasswordActivityNavigation)
    }

    fun onVisitorClicked() {
        navigate(Navigation.HomeActivityNavigation)
    }


    @UiThread
    fun onSignInClicked() {
        if (validateFields()) {
            showBlockProgressBar()
            viewModelScope.launch {
                tryCatch({
                    val responseUser = withContext(schedulerProvider.dispatchersIO()) {
                        userRepository.signInAndCache(email.value!!, password.value!!)
                    }
                    onSignInSuccess(responseUser)
                }, { error ->
                    onSignInError(error)
                })
            }
        }
    }

    private fun onSignInSuccess(signupResponse: SignupResponse) {
        hideBlockProgressBar()
        if (signupResponse.result) {
            navigate(Navigation.HomeActivityNavigation)
        } else if (signupResponse.resultCode == 4) {
            signupResponse.user?.id?.let { Navigation.OtpActivityNavigation(it.toInt()) }
                ?.let { navigate(it) }
/*            shownSimpleDialog(
                messageId = R.string.signin_inactivated_account,
                dismissActionBlock = {
                    navigate(Navigation.OtpActivityNavigation)
                })*/
        } else if (signupResponse.resultCode == 3) {
            shownSimpleDialog(
                messageId = R.string.signin_password_error,
                dismissActionBlock = {

                })
        }
    }

    private fun onSignInError(throwable: Throwable) {
        hideBlockProgressBar()
        if (throwable is HttpException) {
            when (throwable.code()) {
                HttpResponseCode.HTTP_UNAUTHORIZED -> shownSimpleDialog(messageId = R.string.signin_invalid_data)
                HttpResponseCode.HTTP_BAD_REQUEST -> shownSimpleDialog(messageId = R.string.global_error_banned_user)
                else -> handleThrowable(throwable)
            }
        } else {
            handleThrowable(throwable)
        }
    }


    /** Fields validation  **/

    private fun validateFields(): Boolean {
        return validEmail() and validPassword()
    }

    private fun validEmail() = if (email.value.isWhiteSpaces() || !email.value.isValidEmail()) {
        emailFieldError.value = true
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
