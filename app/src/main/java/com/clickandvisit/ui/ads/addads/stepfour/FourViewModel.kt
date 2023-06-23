package com.clickandvisit.ui.ads.addads.stepfour

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.data.model.property.add.PropertyAdd
import com.clickandvisit.global.helper.ImagePicker
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.TAG
import com.clickandvisit.global.utils.toMediaUrl
import com.clickandvisit.ui.shared.dialog.ImgPickerDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val MAIN_PIC_NAME = "mainPic.jpeg"


@HiltViewModel
class FourViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle

) : BaseAndroidViewModel(application, schedulerProvider) {

    val imagePickerDialog: MutableLiveData<ImgPickerDialog> = MutableLiveData()

    val mainPhotoUri = MutableLiveData(Uri.EMPTY)

    val isEmptyUri = mainPhotoUri.map {
        it == null || it == Uri.EMPTY
    }


    init {
        //isEmptyUri.value = mainPhotoUri.value == null || mainPhotoUri.value == Uri.EMPTY
    }


    fun onImageMainClick() {
        imagePickerDialog.value =
            ImgPickerDialog.build(
                onTakePictureClicked(),
                onPickPictureClicked(),
                onDismissBottomSheet()
            )
    }

    fun onCancelImageMainClick() {
        DebugLog.i(TAG, "Canceled...")
        mainPhotoUri.value = Uri.EMPTY
        //isEmptyUri.value = mainPhotoUri.value == null || mainPhotoUri.value == Uri.EMPTY
    }

    private fun onTakePictureClicked(): () -> Unit {
        return {
            navigate(Navigation.CameraNavigation(MAIN_PIC_NAME))
        }
    }

    fun cameraPermissionGranted() {
        navigate(Navigation.CameraNavigation(MAIN_PIC_NAME))
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
                        MAIN_PIC_NAME
                    )
                    ImagePicker.PICK_IMAGE_GALLERY_ID -> ImagePicker.getUriFromResult(
                        applicationContext,
                        resultCode,
                        data,
                        false,
                        MAIN_PIC_NAME
                    )
                    else -> Uri.EMPTY
                }
                picture
            }
            mainPhotoUri.value = picture
            //isEmptyUri.value = mainPhotoUri.value == null || mainPhotoUri.value == Uri.EMPTY
        }
    }


    fun onEditProperty(property: Property) {
        if (property.mainPhoto.toMediaUrl() != "https://")
            mainPhotoUri.value = Uri.parse(property.mainPhoto)
    }


}