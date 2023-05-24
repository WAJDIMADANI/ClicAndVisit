package com.foodline.ui.menu.profile

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.foodline.base.BaseAndroidViewModel
import com.foodline.data.repository.abs.UserRepository
import com.foodline.global.helper.ImagePicker
import com.foodline.global.helper.Navigation
import com.foodline.global.listener.SchedulerProvider
import com.foodline.global.listener.ToolBarListener
import com.foodline.global.utils.DebugLog
import com.foodline.global.utils.TAG
import com.foodline.ui.shared.dialog.ImgPickerDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val PROFILE_PIC_NAME = "profilePic.jpeg"

@HiltViewModel
class ProfileViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {

    val userName: MutableLiveData<String> = MutableLiveData()
    val cin: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val phone: MutableLiveData<String> = MutableLiveData()

    val userFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val cinFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val emailFieldError: MutableLiveData<Boolean> = MutableLiveData(false)
    val phoneFieldError: MutableLiveData<Boolean> = MutableLiveData(false)

    val imagePickerDialog: MutableLiveData<ImgPickerDialog> = MutableLiveData()

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
            navigate(Navigation.CameraNavigation(com.foodline.ui.signup.PROFILE_PIC_NAME))
        }
    }

    fun cameraPermissionGranted() {
        navigate(Navigation.CameraNavigation(com.foodline.ui.signup.PROFILE_PIC_NAME))
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
                        com.foodline.ui.signup.PROFILE_PIC_NAME
                    )
                    ImagePicker.PICK_IMAGE_GALLERY_ID -> ImagePicker.getUriFromResult(
                        applicationContext,
                        resultCode,
                        data,
                        false,
                        com.foodline.ui.signup.PROFILE_PIC_NAME
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


    fun onSaveClicked() {

    }

}
