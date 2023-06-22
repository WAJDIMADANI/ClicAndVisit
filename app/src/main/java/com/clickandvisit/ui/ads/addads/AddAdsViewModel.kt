package com.clickandvisit.ui.ads.addads

import android.app.Application
import android.net.Uri
import android.os.Parcel
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.SearchRequest
import com.clickandvisit.data.model.property.add.PropertyAdd
import com.clickandvisit.data.model.property.add.PropertyAddResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.DebugLog
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
    val postalCode = MutableLiveData<String>()
    val address = MutableLiveData<String>()

    val inter = MutableLiveData<String>()
    val interCode = MutableLiveData<String>()
    val otherInfo = MutableLiveData<String>()

    /** photos **/
    val mainPhotoUri = MutableLiveData(Uri.EMPTY)


    val propertyAdd: MutableLiveData<PropertyAdd> = MutableLiveData()

    lateinit var property: PropertyAdd

    init {
        property=PropertyAdd(null,null)
    }

    fun createOrUpdateProperty() {

        property.propType = if (checkedSale.value == true) {
            30
        } else {
            29
        }

        if (checkedHome.value == true) {
            property.propCategory = 96
        } else if (checkedB.value == true) {
            property.propCategory = 99
        } else if (checkedApp.value == true) {
            property.propCategory = 97
        } else if (checkedTer.value == true) {
            property.propCategory = 100
        } else if (checkedGarage.value == true) {
            property.propCategory = 98
        } else if (checkedComm.value == true) {
            property.propCategory = 101
        }

        //TODO: type de bien
        property.propSurface = surface.value?.toInt()
        property.propPrix = price.value?.toInt()
        //TODO: DPE / GES
        property.propEtage = stage.value?.toInt()
        property.propEtageSur = on.value?.toInt()
        property.propInfos = info.value


        property.prop_meta_chambres = roomNbrApi1.value
        property.prop_meta_suites = roomNbrApi2.value
        property.prop_meta_salles_de_bains = roomNbrApi3.value
        property.prop_meta_salles_d_eau = roomNbrApi4.value
        property.prop_meta_bureaux = roomNbrApi5.value
        property.prop_meta_dressing = roomNbrApi6.value
        property.prop_meta_garages = roomNbrApi17.value
        property.prop_meta_caves = roomNbrApi8.value
        property.prop_meta_balcons = roomNbrApi9.value
        property.prop_meta_terrasse = roomNbrApi10.value
        //property?.prop_meta_surface_terrain = roomNbrApi1.value
        property.prop_meta_annee = roomNbrApi12.value
        property.prop_meta_piscine = roomNbrApi13.value
        property.prop_meta_piscinable = roomNbrApi14.value
        property.prop_meta_pool_house = roomNbrApi15.value
        property.prop_meta_sans_vis_a_vis = roomNbrApi16.value
        property.prop_meta_ascenseur = roomNbrApi17.value
        property.prop_meta_duplex = roomNbrApi18.value
        property.prop_meta_triplex = roomNbrApi19.value
        property.prop_meta_rez_de_jardin = roomNbrApi20.value


        property.prop_localisation_ville = city.value!!
        property.prop_localisation_codepostal = postalCode.value!!
        property.prop_localisation_complement_adresse = address.value!!
        property.proInterphoneName = inter.value
        property.propCodeportail = interCode.value
        property.propInfos = otherInfo.value


        //FIXME: update photo ws call property?.propMainPhoto

        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                val propertyAddResponse = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.createUpdateProperty(
                        property
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
    }

    private fun onCreateOrUpdatePropertyError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }

}