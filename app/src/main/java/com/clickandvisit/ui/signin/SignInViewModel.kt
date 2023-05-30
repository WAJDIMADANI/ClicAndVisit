package com.clickandvisit.ui.signin

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.user.User
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.helper.NonNullLiveData
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.*
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
    BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {


    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val emailFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val passwordFieldError: MutableLiveData<Boolean> = MutableLiveData(false)


    val showPassword: NonNullLiveData<Boolean> = NonNullLiveData(false)
    val signingIn: NonNullLiveData<Boolean> = NonNullLiveData(false)


    fun onForgetPasswordClicked() {
        navigate(Navigation.ResetPasswordActivityNavigation)
    }

    fun onSignUpClicked() {
        navigate(Navigation.SignUpActivityNavigation)
    }

    fun onVisitorClicked() {
        navigate(Navigation.HomeActivityNavigation)
    }

    override fun onStartClicked() {
        navigate(Navigation.Back)
    }

    fun onShowPasswordClicked(show: Boolean) {
        showPassword.value = show
    }

    @UiThread
    fun onSignInClicked() {
        navigate(Navigation.HomeActivityNavigation)
        if (validateFields()) {
            viewModelScope.launch {
                signingIn.value = true
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


    private fun onSignInSuccess(userResponse: User) {
        signingIn.value = false
        navigate(Navigation.HomeActivityNavigation)
    }

    private fun onSignInError(throwable: Throwable) {
        signingIn.value = false
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
