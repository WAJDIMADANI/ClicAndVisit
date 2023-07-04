package com.clickandvisit.ui.user.profile

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.user.User
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.ImagePicker
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.isValidEmail
import com.clickandvisit.global.utils.isWhiteSpaces
import com.clickandvisit.global.utils.toMediaUrl
import com.clickandvisit.global.utils.tryCatch
import com.clickandvisit.ui.shared.dialog.ImgPickerDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


const val PROFILE_PIC_NAME = "profile_pic_name.jpeg"

@HiltViewModel
class ProfileViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {


    val firstName: MutableLiveData<String> = MutableLiveData()
    val lastName: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val phone: MutableLiveData<String> = MutableLiveData()

    /** no pro **/
    val checkedM: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedF: MutableLiveData<Boolean> = MutableLiveData(false)


    val firstNameFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val lastNameFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val emailFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val phoneFieldError: MutableLiveData<Boolean> = MutableLiveData(false)

    val imagePickerDialog: MutableLiveData<ImgPickerDialog> = MutableLiveData()

    val photoUri = MutableLiveData(Uri.EMPTY)//FIXME: update photo ws call

    lateinit var user: User
    var isChanged: Boolean = false

    val checkedPro: MutableLiveData<Boolean> = MutableLiveData(false)


    init {
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                user = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getUser()
                }
                onGetProfileSuccess(user)
            }, { error ->
                onGetProfileError(error)
            })
        }

    }

    private fun onGetProfileSuccess(user: User) {
        hideBlockProgressBar()
        checkedPro.value = user.isPro()
        checkProPar(user)
        email.value = user.email
        phone.value = user.phoneNumber

        if (user.photo.toMediaUrl() != "https://")
            photoUri.value = Uri.parse(user.photo)

    }

    private fun checkProPar(user: User) {
        if (user.isPro()) {
            firstName.value = user.rSocial
            lastName.value = user.siret
        } else {
            firstName.value = user.firstName
            lastName.value = user.lastName
            checkCivility(user)
        }
    }

    private fun checkCivility(user: User) {
        if (user.civility.toInt() == 0) {
            checkedM.value = true
        } else {
            checkedF.value = true
        }
    }

    private fun onGetProfileError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
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
            isChanged = true
        }
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    fun onChangePasswordClicked() {
        // TODO: Query
    }

    fun onDeleteClick() {
        // TODO: Query
    }

    fun onSaveClicked() {
        if (validateFields()) {
            showBlockProgressBar()
            if (user.isPro()) {
                user.rSocial = firstName.value!!
                user.siret = lastName.value!!
            } else {
                user.firstName = firstName.value!!
                user.lastName = lastName.value!!
                user.civility = getGenderMFId().toString()
            }

            user.email = email.value!!
            user.phoneNumber = phone.value!!

            val photoFile = File(photoUri.value?.path ?: "")


            if (isChanged) {
                val requestFile: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), photoFile)
                val body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("profile_photo", photoFile.name, requestFile)

                val requestUserId = user.id.toRequestBody("text/plain".toMediaTypeOrNull())


                viewModelScope.launch {
                    tryCatch({
                        val user = withContext(schedulerProvider.dispatchersIO()) {
                            userRepository.userUpdate(requestUserId, body)
                        }
                        userUpdate()
                    }, { error ->
                        onSetProfileError(error)
                    })
                }
            } else {
                userUpdate()
            }


        }
    }

    private fun userUpdate() {
        viewModelScope.launch {
            tryCatch({
                val user = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.userUpdate(user)
                }
                onSetProfileSuccess(user)
            }, { error ->
                onSetProfileError(error)
            })
        }
    }

    private fun onSetProfileSuccess(user: User) {
        hideBlockProgressBar()
        navigate(Navigation.Back)
    }

    private fun onSetProfileError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }

    private fun getGenderMFId(): Int {
        return if (checkedM.value == true) {
            0
        } else {
            1
        }
    }

    fun onMClick() {
        checkedM.value = true
        checkedF.value = false
    }

    fun onFClick() {
        checkedM.value = false
        checkedF.value = true
    }


    /** Fields validation  **/

    private fun validateFields(): Boolean {
        return validFirstName() and validLastName() and validEmail() and validPhone()
    }

    private fun validFirstName() = if (firstName.value.isWhiteSpaces()) {
        firstNameFieldError.value = true
        false
    } else {
        true
    }

    private fun validLastName() = if (lastName.value.isWhiteSpaces()) {
        lastNameFieldError.value = true
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


}