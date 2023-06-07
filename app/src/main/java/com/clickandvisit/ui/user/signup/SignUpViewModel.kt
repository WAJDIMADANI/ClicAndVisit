package com.clickandvisit.ui.user.signup

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.user.User
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.ImagePicker
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.*
import com.clickandvisit.ui.shared.dialog.ImgPickerDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject


const val PROFILE_PIC_NAME = "profilePic.jpeg"


@HiltViewModel
class SignUpViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {

    val userName: MutableLiveData<String> = MutableLiveData()
    val firstname: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val phone: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val passwordConfirm: MutableLiveData<String> = MutableLiveData()

    val userFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val firstnameFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val emailFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val phoneFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val passwordFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val passwordConfirmFieldError: MutableLiveData<Boolean> = MutableLiveData(false)

    /** Radio buttons states  **/
    val checkedPro: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedNoPro: MutableLiveData<Boolean> = MutableLiveData(true)

    val imagePickerDialog: MutableLiveData<ImgPickerDialog> = MutableLiveData()

    val photoUri = MutableLiveData(Uri.EMPTY)

    init {

    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    fun onImageClick() {
        imagePickerDialog.value =
            ImgPickerDialog.build(
                onTakePictureClicked(),
                onPickPictureClicked(),
                onDismissBottomSheet()
            )
    }

    private fun onTakePictureClicked(): () -> Unit {
        return {
            navigate(Navigation.CameraNavigation(PROFILE_PIC_NAME))
        }
    }

    fun cameraPermissionGranted() {
        navigate(Navigation.CameraNavigation(PROFILE_PIC_NAME))
    }

    private fun onPickPictureClicked(): () -> Unit {
        return {
            navigate(Navigation.GalleryNavigation)
        }
    }

    private fun onDismissBottomSheet(): () -> Unit {
        return {
            imagePickerDialog.value = null
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModelScope.launch {
            val picture = withContext(schedulerProvider.dispatchersIO()) {
                val picture: Uri? = when (requestCode) {
                    ImagePicker.PICK_IMAGE_CAMERA_ID -> ImagePicker.getUriFromResult(
                        applicationContext,
                        resultCode,
                        data,
                        true,
                        PROFILE_PIC_NAME
                    )
                    ImagePicker.PICK_IMAGE_GALLERY_ID -> ImagePicker.getUriFromResult(
                        applicationContext,
                        resultCode,
                        data,
                        false,
                        PROFILE_PIC_NAME
                    )
                    else -> Uri.EMPTY
                }
                picture
            }
            photoUri.value = picture
            DebugLog.i(TAG, picture.toString())
        }
    }

    override fun onStartClicked() {
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


    fun onSignInClicked() {
        navigate(Navigation.SignInActivityNavigation)
    }

    /** Fields validation  **/

    private fun validateFields(): Boolean {
        return validUserName() and validCin() and validEmail() and validPhone() and validPassword()
    }


    private fun validUserName() = if (userName.value.isWhiteSpaces()) {
        userFieldError.value = true
        false
    } else {
        true
    }

    private fun validCin() = if (firstname.value.isWhiteSpaces()) {
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
