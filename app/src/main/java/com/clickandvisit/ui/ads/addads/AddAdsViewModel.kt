package com.clickandvisit.ui.ads.addads

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.data.model.property.add.PropertyAdd
import com.clickandvisit.data.model.property.add.PropertyAddResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class AddAdsViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider) {

    /** step one **/
    val checkedSale: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedRent: MutableLiveData<Boolean> = MutableLiveData(false)

    val checkedHome: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedB: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedApp: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedTer: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedGarage: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedComm: MutableLiveData<Boolean> = MutableLiveData(false)

    val checked1: MutableLiveData<Boolean> = MutableLiveData(false)
    val checked2: MutableLiveData<Boolean> = MutableLiveData(false)
    val checked3: MutableLiveData<Boolean> = MutableLiveData(false)
    val checked4: MutableLiveData<Boolean> = MutableLiveData(false)
    val checked5: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedNA: MutableLiveData<Boolean> = MutableLiveData(false)

    val surface: MutableLiveData<String> = MutableLiveData()
    val price: MutableLiveData<String> = MutableLiveData()

    val stage: MutableLiveData<String> = MutableLiveData()
    val on: MutableLiveData<String> = MutableLiveData()

    val info: MutableLiveData<String> = MutableLiveData()

    val ges: MutableLiveData<String> = MutableLiveData()
    val dpe: MutableLiveData<String> = MutableLiveData()

    /** room details **/
    val roomNbrApi1: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi2: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi3: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi4: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi5: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi6: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi7: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi8: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi9: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi10: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi12: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi13: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi14: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi15: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi16: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi17: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi18: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi19: MutableLiveData<String> = MutableLiveData("")
    val roomNbrApi20: MutableLiveData<String> = MutableLiveData("")


    /** localisation **/
    val city = MutableLiveData<String>()
    val postalCode = MutableLiveData<String?>()
    val address = MutableLiveData<String>()

    val inter = MutableLiveData<String>()
    val interCode = MutableLiveData<String>()
    val otherInfo = MutableLiveData<String>()

    /** photos **/
    val mainPhotoUri = MutableLiveData(Uri.EMPTY)
    val photoList: MutableLiveData<ArrayList<Uri>> = MutableLiveData(arrayListOf())

    /** calendar **/
    var datesTimes : String = ""


    val propertyAdd: MutableLiveData<PropertyAdd> = MutableLiveData(PropertyAdd())

    val propertyEdit = MutableLiveData<Property>()

    val isEdit: MutableLiveData<Boolean> = MutableLiveData()
    val isMeet: MutableLiveData<Boolean> = MutableLiveData()


    var propId: Int = 0

    init {
        isEdit.value =
            savedStateHandle.getLiveData<Boolean>(ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_EDIT).value

        if (isEdit.value == true) {
            propertyEdit.value =
                savedStateHandle.getLiveData<Property>(ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_PROP).value
        }
        isMeet.value =
            savedStateHandle.getLiveData<Boolean>(ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_MEET).value

        if (isMeet.value == true){
            propId = propertyEdit.value?.id!!
        }
    }


    fun createOrUpdateProperty() {

        if (isEdit.value == true) {
            propertyAdd.value!!.propId = propertyEdit.value!!.id
        }

        propertyAdd.value?.propType = getPropType()

        propertyAdd.value?.propCategory = getPropCategory()

        propertyAdd.value?.propPieces = getPiecesNbr()
        propertyAdd.value?.prop_meta_chambres = roomNbrApi1.value

        propertyAdd.value?.propSurface = surface.value?.toInt()
        propertyAdd.value?.propPrix = price.value



        propertyAdd.value?.propGes = if (ges.value.isNullOrEmpty()) {
            ""
        } else {
            ges.value
        }

        propertyAdd.value?.propEnery = if (dpe.value.isNullOrEmpty()) {
            ""
        } else {
            dpe.value
        }

        try {
            propertyAdd.value?.propEtage = stage.value?.toInt()
        } catch (e: NumberFormatException) {
            propertyAdd.value?.propEtage = null
        }

        try {
            propertyAdd.value?.propEtageSur = on.value?.toInt()
        } catch (e: NumberFormatException) {
            propertyAdd.value?.propEtageSur = null
        }

        propertyAdd.value?.infoComp = info.value

        propertyAdd.value?.prop_meta_suites = roomNbrApi2.value
        propertyAdd.value?.prop_meta_salles_de_bains = roomNbrApi3.value
        propertyAdd.value?.prop_meta_salles_d_eau = roomNbrApi4.value
        propertyAdd.value?.prop_meta_bureaux = roomNbrApi5.value
        propertyAdd.value?.prop_meta_dressing = roomNbrApi6.value
        propertyAdd.value?.prop_meta_garages = roomNbrApi17.value
        propertyAdd.value?.prop_meta_caves = roomNbrApi8.value
        propertyAdd.value?.prop_meta_balcons = roomNbrApi9.value
        propertyAdd.value?.prop_meta_terrasse = roomNbrApi10.value
        //property?.prop_meta_surface_terrain = roomNbrApi1.value
        propertyAdd.value?.prop_meta_annee = roomNbrApi12.value
        propertyAdd.value?.prop_meta_piscine = roomNbrApi13.value
        propertyAdd.value?.prop_meta_piscinable = roomNbrApi14.value
        propertyAdd.value?.prop_meta_pool_house = roomNbrApi15.value
        propertyAdd.value?.prop_meta_sans_vis_a_vis = roomNbrApi16.value
        propertyAdd.value?.prop_meta_ascenseur = roomNbrApi17.value
        propertyAdd.value?.prop_meta_duplex = roomNbrApi18.value
        propertyAdd.value?.prop_meta_triplex = roomNbrApi19.value
        propertyAdd.value?.prop_meta_rez_de_jardin = roomNbrApi20.value


        propertyAdd.value?.prop_localisation_ville = city.value
        propertyAdd.value?.prop_localisation_codepostal = postalCode.value
        propertyAdd.value?.prop_localisation_complement_adresse = address.value
        propertyAdd.value?.proInterphoneName = inter.value
        propertyAdd.value?.propCodeportail = interCode.value
        propertyAdd.value?.propInfos = otherInfo.value


        propertyAdd.value?.propMainPhoto = try {
            createImageFileFormData(
                mainPhotoUri.value?.path ?: "",
                "prop_main_photo"
            )
        } catch (e: Exception) {
            null
        }

        propertyAdd.value?.propAlbumPhoto1 = try {
            createImageFileFormData(
                photoList.value?.get(0)?.path ?: "",
                "prop_album_photo[0]"
            )
        } catch (e: Exception) {
            null
        }

        propertyAdd.value?.propAlbumPhoto2 = try {
            createImageFileFormData(
                photoList.value?.get(1)?.path ?: "",
                "prop_album_photo[1]"
            )
        } catch (e: Exception) {
            null
        }


        propertyAdd.value?.propAlbumPhoto3 = try {
            createImageFileFormData(
                photoList.value?.get(2)?.path ?: "",
                "prop_album_photo[2]"
            )
        } catch (e: Exception) {
            null
        }


        propertyAdd.value?.propAlbumPhoto4 = try {
            createImageFileFormData(
                photoList.value?.get(3)?.path ?: "",
                "prop_album_photo[3]"
            )
        } catch (e: Exception) {
            null
        }


        propertyAdd.value?.propAlbumPhoto5 = try {
            createImageFileFormData(
                photoList.value?.get(4)?.path ?: "",
                "prop_album_photo[4]"
            )
        } catch (e: Exception) {
            null
        }


        propertyAdd.value?.propAlbumPhoto6 = try {
            createImageFileFormData(
                photoList.value?.get(5)?.path ?: "",
                "prop_album_photo[5]"
            )
        } catch (e: Exception) {
            null
        }

        propertyAdd.value!!.propPrix = propertyAdd.value!!.propPrix?.replace(" ", "")
        propertyAdd.value!!.propSurface =
            propertyAdd.value!!.propSurface?.toString()?.replace(" ", "")?.toInt()
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                val propertyAddResponse = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.createUpdateProperty(
                        propertyAdd.value!!,
                        datesTimes
                    )
                }
                createUpdatePropertySuccess(propertyAddResponse)
            }, { error ->
                onCreateOrUpdatePropertyError(error)
            })
        }
    }

    private fun getPropType() = if (checkedSale.value == true) {
        29
    } else {
        30
    }

    private fun getPropCategory() = if (checkedHome.value == true) {
        96
    } else if (checkedB.value == true) {
        99
    } else if (checkedApp.value == true) {
        97
    } else if (checkedTer.value == true) {
        100
    } else if (checkedGarage.value == true) {
        98
    } else if (checkedComm.value == true) {
        101
    } else {
        96
    }

    private fun getPiecesNbr() = if (checked1.value == true) {
        "1"
    } else if (checked2.value == true) {
        "2"
    } else if (checked3.value == true) {
        "3"
    } else if (checked4.value == true) {
        "4"
    } else if (checked5.value == true) {
        "5"
    } else if (checkedNA.value == true) {
        "NA"
    } else null

    private fun createImageFileFormData(pathname: String, photoWsName: String): MultipartBody.Part {
        val mainPhotoFile = File(pathname)
        val requestFile: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), mainPhotoFile)
        return MultipartBody.Part.createFormData(
            photoWsName,
            System.currentTimeMillis().toString() + "_" + mainPhotoFile.name,
            requestFile
        )
    }

    private fun createUpdatePropertySuccess(propertyAddResponse: PropertyAddResponse) {
        hideBlockProgressBar()
        propId = propertyAddResponse.propId
        //FIXME: show success popup,   ok -> navigate(Navigation.CalendarFragmentNavigation)
        navigate(Navigation.CalendarFragmentNavigation)
    }

    private fun onCreateOrUpdatePropertyError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }

}