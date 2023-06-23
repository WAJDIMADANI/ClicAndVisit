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
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.global.utils.TAG
import com.clickandvisit.global.utils.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    val info: MutableLiveData<String?> = MutableLiveData()


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


    val propertyAdd: MutableLiveData<PropertyAdd> = MutableLiveData(PropertyAdd())

    val propertyEdit = MutableLiveData<Property>()

    val isEdit: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isEdit.value =
            savedStateHandle.getLiveData<Boolean>(ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_EDIT).value

        if (isEdit.value == true) {
            propertyEdit.value =
                savedStateHandle.getLiveData<Property>(ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_PROP).value

        }
    }


    fun createOrUpdateProperty() {

        if (isEdit.value == true) {
            propertyAdd.value!!.propId = propertyEdit.value!!.id
        }

        propertyAdd.value?.propType = if (checkedSale.value == true) {
            30
        } else {
            29
        }

        if (checkedHome.value == true) {
            propertyAdd.value?.propCategory = 96
        } else if (checkedB.value == true) {
            propertyAdd.value?.propCategory = 99
        } else if (checkedApp.value == true) {
            propertyAdd.value?.propCategory = 97
        } else if (checkedTer.value == true) {
            propertyAdd.value?.propCategory = 100
        } else if (checkedGarage.value == true) {
            propertyAdd.value?.propCategory = 98
        } else if (checkedComm.value == true) {
            propertyAdd.value?.propCategory = 101
        }

        // S+...
        propertyAdd.value?.prop_meta_chambres = if (checked1.value == true) {
            "1"
        } else if (checked2.value == true) {
            "2"
        } else if (checked3.value == true) {
            "3"
        } else if (checked4.value == true) {
            "4"
        } else if (checked5.value == true) {
            "5 et +"
        } else if (checkedNA.value == true) {
            "NA"
        } else null


        //TODO: type de bien
        propertyAdd.value?.propSurface = surface.value?.toInt()
        propertyAdd.value?.propPrix = price.value?.toInt()
        //TODO: DPE / GES
        try {
            propertyAdd.value?.propEtage = stage.value?.toInt()
        }catch (e: NumberFormatException){
            propertyAdd.value?.propEtage = null
        }

        try {
            propertyAdd.value?.propEtageSur = on.value?.toInt()
        }catch (e: NumberFormatException){
            propertyAdd.value?.propEtageSur = null
        }

        propertyAdd.value?.propInfos = info.value


        //FIXME: query Saif; propertyAdd.value?.prop_meta_chambres = roomNbrApi1.value
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


        //FIXME: update photo ws call property?.propMainPhoto

        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                val propertyAddResponse = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.createUpdateProperty(
                        propertyAdd.value!!
                    )
                }
                onCreateOrUpdatePropertySuccess(propertyAddResponse)
            }, { error ->
                onCreateOrUpdatePropertyError(error)
            })
        }
    }


    private fun onCreateOrUpdatePropertySuccess(propertyAddResponse: PropertyAddResponse) {
        hideBlockProgressBar()
        DebugLog.i(TAG, propertyAddResponse.toString())
        navigate(Navigation.CalendarFragmentNavigation)
    }

    private fun onCreateOrUpdatePropertyError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }

}