package com.clickandvisit.ui.ads.addads.stepfour

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.ImagePicker
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.CancelClickedListener
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.*
import com.clickandvisit.ui.shared.dialog.ImgPickerDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

const val MAIN_PIC_NAME = "mainPic.jpeg"

const val PIC_NAME = "picPhotos.jpeg"
const val REQUEST_CODE_GALLERY_PIC = 111
const val REQUEST_CODE_CAMERA_PIC = 222


@HiltViewModel
class FourViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), CancelClickedListener {

    val imagePickerDialog: MutableLiveData<ImgPickerDialog?> = MutableLiveData()

    val mainPhotoUri = MutableLiveData(Uri.EMPTY)
    val isEmptyUri = mainPhotoUri.map { it == null || it == Uri.EMPTY }


    val photoList: MutableLiveData<ArrayList<Uri>> = MutableLiveData(arrayListOf())

    private var list = arrayListOf<Uri>()


    init {

    }


    fun onEditProperty(property: Property) {
        if (property.mainPhoto.toMediaUrl() != "https://")
            property.mainPhoto?.let { getFileFromUrl(it) }

        property.album?.forEach {
            getFilesAlbumFromUrl(it)
        }
        photoList.value = list
    }


    private fun getFilesAlbumFromUrl(url: String) {
        viewModelScope.launch {
            list.add(Uri.fromFile(withContext(schedulerProvider.dispatchersIO()) {
                val responseBody = userRepository.downloadFile(url).body()
                val pathWhereYouWantToSaveFile =
                    applicationContext.filesDir.absolutePath + System.currentTimeMillis()
                        .toString() + "default_photo.jpg"
                val savedFile = saveFile(responseBody, pathWhereYouWantToSaveFile)
                return@withContext File(savedFile)
            }
            ))
            photoList.value = list
        }
    }

    private fun getFileFromUrl(url: String) {
        viewModelScope.launch {
            tryCatch({
                val photoFile = withContext(schedulerProvider.dispatchersIO()) {
                    val responseBody = userRepository.downloadFile(url).body()
                    val pathWhereYouWantToSaveFile =
                        applicationContext.filesDir.absolutePath + System.currentTimeMillis()
                            .toString() + "default_photo.jpg"
                    val savedFile = saveFile(responseBody, pathWhereYouWantToSaveFile)
                    return@withContext File(savedFile)
                }
                mainPhotoUri.value = Uri.fromFile(photoFile)
            }, {
                mainPhotoUri.value = Uri.EMPTY
            })
        }
    }

    fun cameraPermissionGranted() {
        navigate(Navigation.CameraNavigation(MAIN_PIC_NAME))
    }

    fun onCancelImageMainClick() {
        DebugLog.i(TAG, "Canceled...")
        mainPhotoUri.value = Uri.EMPTY
    }


    override fun onCancelImageClick(position: Int) {
        list.removeAt(position)
        photoList.value = list
    }


    private fun onDismissBottomSheet(): () -> Unit {
        return {
            imagePickerDialog.value = null
        }
    }

    /** 1 **/
    fun onImageMainClick() {
        imagePickerDialog.value =
            ImgPickerDialog.build(
                onCameraTakePictureClicked(MAIN_PIC_NAME),
                onGalleryPickPictureClicked(),
                onDismissBottomSheet()
            )
    }


    fun onImageClick() {
        imagePickerDialog.value =
            ImgPickerDialog.build(
                onCameraTakePictureClicked(PIC_NAME),
                onGalleryPickPicturesClicked(REQUEST_CODE_GALLERY_PIC),
                onDismissBottomSheet()
            )
    }

    /** 2 **/
    private fun onCameraTakePictureClicked(name: String): () -> Unit {
        return {
            navigate(Navigation.CameraNavigation(name))
        }
    }

    /** 2 **/
    private fun onGalleryPickPictureClicked(): () -> Unit {
        return {
            navigate(Navigation.GalleryNavigation)
        }
    }

    private fun onGalleryPickPicturesClicked(name: Int): () -> Unit {
        return {
            navigate(Navigation.GalleriesNavigation(name))
        }
    }

    /** 5 **/
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        DebugLog.i("REQUEST_CODE", requestCode.toString())
        viewModelScope.launch {
            when (requestCode) {

                ImagePicker.PICK_IMAGE_CAMERA_ID -> {
                    mainPhotoUri.value = withContext(schedulerProvider.dispatchersIO()) {
                        ImagePicker.getUriFromResult(
                            applicationContext,
                            resultCode,
                            data,
                            true,
                            MAIN_PIC_NAME
                        )
                    }
                }

                ImagePicker.PICK_IMAGE_GALLERY_ID -> {
                    mainPhotoUri.value = withContext(schedulerProvider.dispatchersIO()) {
                        ImagePicker.getUriFromResult(
                            applicationContext,
                            resultCode,
                            data,
                            false,
                            MAIN_PIC_NAME
                        )
                    }
                }

                REQUEST_CODE_CAMERA_PIC -> {
                    list.add(withContext(schedulerProvider.dispatchersIO()) {
                        ImagePicker.getUriFromResult(
                            applicationContext,
                            resultCode,
                            data,
                            true,
                            System.currentTimeMillis().toString() + PIC_NAME
                        )
                    })

                    photoList.value = list
                }

                REQUEST_CODE_GALLERY_PIC -> {
                    list.add(withContext(schedulerProvider.dispatchersIO()) {
                        ImagePicker.getUriFromResult(
                            applicationContext,
                            resultCode,
                            data,
                            false,
                            System.currentTimeMillis().toString() + PIC_NAME
                        )
                    })
                    photoList.value = list
                }

            }
        }
    }


}