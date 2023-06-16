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
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.TAG
import com.clickandvisit.global.utils.toMediaUrl
import com.clickandvisit.global.utils.tryCatch
import com.clickandvisit.ui.home.menu.profile.PROFILE_PIC_NAME
import com.clickandvisit.ui.shared.dialog.ImgPickerDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


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

    val photoUri = MutableLiveData(Uri.EMPTY)

    lateinit var user: User

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

    private fun onGetProfileSuccess(user: User) { //TODO: if pro + edit
        hideBlockProgressBar()

        checkedPro.value = user.isPro()

        if (user.isPro()) {
            firstName.value = user.rSocial
            lastName.value = user.siret
        } else {
            firstName.value = user.firstName
            lastName.value = user.lastName
        }

        email.value = user.email
        phone.value = user.phoneNumber

        if (user.civility.toInt() == 0) {
            checkedM.value = true
        } else {
            checkedF.value = true
        }

        if (user.photo.toMediaUrl() != "https://")
            photoUri.value = Uri.parse(user.photo)

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
            DebugLog.i(TAG, picture.toString())
        }
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    fun onChangePasswordClicked() {

    }

    fun onDeleteClick() {

    }

    fun onSaveClicked() {

    }

}