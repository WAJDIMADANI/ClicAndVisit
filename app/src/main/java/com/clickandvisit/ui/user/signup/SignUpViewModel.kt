package com.clickandvisit.ui.user.signup

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.user.signup.SignupResponse
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

const val PROFILE_PIC_NAME = "profilePic.jpeg"

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

    val imagePickerDialog: MutableLiveData<ImgPickerDialog?> = MutableLiveData()

    val photoUri = MutableLiveData(Uri.EMPTY)

    init {

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
        }
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

    private fun isPro(): Int {
        return if (checkedPro.value == true) {
            1
        } else {
            2
        }
    }

    private fun isMF(): Int {
        return if (checkedM.value == true) {
            0
        } else {
            1
        }
    }

    @UiThread
    fun onSignUpClicked() {
        if (validateFields()) {
            if (photoUri.value == Uri.EMPTY) {
               /* viewModelScope.launch {
                    showBlockProgressBar()
                    tryCatch({
                        val signupResponse = withContext(schedulerProvider.dispatchersIO()) {
                            userRepository.signUp(
                                proPar = isPro(),
                                siret = siret.value ?: "",
                                rSocial = social.value ?: "",
                                civility = isMF(),
                                firstName = firstname.value ?: "",
                                lastName = userName.value ?: "",
                                email = email.value!!,
                                password = password.value!!,
                                phoneNumber = phone.value!!
                            )
                        }
                        onSignUpSuccess(signupResponse)
                    }, { error ->
                        onSignInError(error)
                    })
                }*/

                viewModelScope.launch {
                    showBlockProgressBar()

                    //val photoFile = File(photoUri.value?.path ?: "")
                    //val requestBody = photoFile.asRequestBody(photoFile.extension.toMediaTypeOrNull())
                    //val requestImage: MultipartBody.Part = MultipartBody.Part.createFormData("profile_photo", photoFile.name, requestBody)

                    val isPro = isPro().toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val siret = siret.value?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val rSocial = social.value?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val civility = isMF().toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val firstName = firstname.value?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val lastName = userName.value?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val email = email.value?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val password = password.value?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val phoneNumber = phone.value?.toRequestBody("text/plain".toMediaTypeOrNull())

                    tryCatch({
                        val signupResponse = withContext(schedulerProvider.dispatchersIO()) {
                            userRepository.signUpWithPhoto(
                                proPar = isPro,
                                siret = siret,
                                rSocial = rSocial,
                                civility = civility,
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                password = password,
                                phoneNumber = phoneNumber,
                                file = null
                            )
                        }
                        onSignUpSuccess(signupResponse)
                    }, { error ->
                        onSignInError(error)
                    })
                }

            } else {
                viewModelScope.launch {
                    showBlockProgressBar()

                    val photoFile = File(photoUri.value?.path ?: "")
                    val requestBody =
                        photoFile.asRequestBody(photoFile.extension.toMediaTypeOrNull())

                    val requestImage: MultipartBody.Part = MultipartBody.Part.createFormData("profile_photo", photoFile.name, requestBody)

                    val isPro = isPro().toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val siret = siret.value?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val rSocial = social.value?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val civility = isMF().toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val firstName = firstname.value?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val lastName = userName.value?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val email = email.value?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val password = password.value?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val phoneNumber = phone.value?.toRequestBody("text/plain".toMediaTypeOrNull())

                    tryCatch({
                        val signupResponse = withContext(schedulerProvider.dispatchersIO()) {
                            userRepository.signUpWithPhoto(
                                proPar = isPro,
                                siret = siret,
                                rSocial = rSocial,
                                civility = civility,
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                password = password,
                                phoneNumber = phoneNumber,
                                file = requestImage
                            )
                        }
                        onSignUpSuccess(signupResponse)
                    }, { error ->
                        onSignInError(error)
                    })
                }
            }
        }
    }


    private fun onSignUpSuccess(signupResponse: SignupResponse) {
        hideBlockProgressBar()
        if (signupResponse.user != null){
            navigate(Navigation.OtpActivityNavigation(signupResponse.user.id.toInt()))
        }else{
            if (signupResponse.resultCode == 8){
                shownSimpleDialog(messageId = R.string.global_error_email_exist)
            }else{
                shownServerErrorSimpleDialog()
            }
        }

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
            validSocial() and validSiret() and validEmail() and validPhone() and validPassword()
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

    private fun validSocial() = if (social.value.isWhiteSpaces()) {
        socialFieldError.value = true
        false
    } else {
        true
    }

    private fun validSiret() = if (siret.value.isWhiteSpaces()) {
        siretFieldError.value = true
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
