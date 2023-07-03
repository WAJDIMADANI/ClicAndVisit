package com.clickandvisit.ui.ads.adsdetails;

import android.app.Application
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.GlobalResponse
import com.clickandvisit.data.model.property.ADD
import com.clickandvisit.data.model.property.FavoriteRequest
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.data.model.property.REMOVE
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.global.utils.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AdsDetailsViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider) {


    val property = MutableLiveData<Property>()

    val imgCount = MutableLiveData<String>()

    val adsName = MutableLiveData<String>()
    val propPrice = MutableLiveData<String>()
    val rooms = MutableLiveData<String>()
    val surface = MutableLiveData<String>()
    val info = MutableLiveData<String>()

    val isFavourite = MutableLiveData<Boolean>()
    val checkedPro: MutableLiveData<Boolean> = MutableLiveData(false)


    val list: MutableLiveData<List<String>> = MutableLiveData(arrayListOf())

    var roomsList = arrayListOf<String>()

    init {
        property.value =
            savedStateHandle.getLiveData<Property>(ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_PROP).value

        fetchImgCount()
        getPrice()
        fetchRoomsDetails()

        fetchDPE()
        fetchGES()

        checkedPro.value = proVisibility()
        adsName.value = property.value!!.title.toUpperCase()
        rooms.value = property.value!!.details.getRoomsNBRDetails()
        surface.value = property.value!!.surface + "\n" + applicationContext.getString(
            R.string.home_details_m_square
        )
        info.value = property.value!!.otherDetails
        isFavourite.value = property.value!!.isFavorite

    }

    private fun fetchDPE(){
        if (property.value!!.energy.isNotEmpty()){
            navigate(Navigation.DPENavigation(property.value!!.energy))
        }
    }

    private fun fetchGES(){
        Handler().postDelayed({
            if (property.value!!.ges.isNotEmpty()){
                navigate(Navigation.GESNavigation(property.value!!.ges))
            }
        }, 500)
    }

    private fun fetchImgCount() {
        imgCount.value = if (property.value!!.mainPhoto.isNullOrEmpty().not()) {
            property.value!!.album.size.plus(1).toString()
        } else {
            property.value!!.album.size.toString()
        }
    }

    private fun fetchRoomsDetails() {
        if (property.value!!.details.chambres.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_1) + property.value!!.details.chambres)
        }

        if (property.value!!.details.suites.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_2) + property.value!!.details.suites)
        }

        if (property.value!!.details.sallesDeBain.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_3) + property.value!!.details.sallesDeBain)
        }

        if (property.value!!.details.sallesDeau.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_4) + property.value!!.details.sallesDeau)
        }

        if (property.value!!.details.bureaux.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_5) + property.value!!.details.bureaux)
        }

        if (property.value!!.details.dressing.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_6) + property.value!!.details.dressing)
        }

        if (property.value!!.details.garages.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_7) + property.value!!.details.garages)
        }

        if (property.value!!.details.caves.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_8) + property.value!!.details.caves)
        }

        if (property.value!!.details.balcons.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_9) + property.value!!.details.balcons)
        }

        if (property.value!!.details.terrasse.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_10) + property.value!!.details.terrasse)
        }

        if (property.value!!.details.surfaceTerrain.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_11) + property.value!!.details.surfaceTerrain)
        }

        if (property.value!!.details.annee.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_12) + property.value!!.details.annee)
        }

        if (property.value!!.details.piscine.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_13) + property.value!!.details.piscine)
        }

        if (property.value!!.details.piscinable.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_14) + property.value!!.details.piscinable)
        }

        if (property.value!!.details.poolHouse.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_15) + property.value!!.details.poolHouse)
        }

        if (property.value!!.details.sansVisAVis.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_16) + property.value!!.details.sansVisAVis)
        }

        if (property.value!!.details.ascenseur.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_17) + property.value!!.details.ascenseur)
        }

        if (property.value!!.details.duplex.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_18) + property.value!!.details.duplex)
        }

        if (property.value!!.details.triplex.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_19) + property.value!!.details.triplex)
        }

        if (property.value!!.details.rezDeJardin.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_20) + property.value!!.details.rezDeJardin)
        }

        list.value = roomsList
    }

    private fun getPrice() {
        if (property.value!!.category.isEmpty() && property.value!!.price.isEmpty()) {
            propPrice.value = ""
        } else if (property.value!!.category.isEmpty().not() && property.value!!.price.isEmpty()) {
            propPrice.value = property.value!!.category
        } else if (property.value!!.category.isEmpty() && property.value!!.price.isEmpty().not()) {
            propPrice.value = property.value!!.getPriceNBR()
        } else {
            propPrice.value = property.value!!.getCategories() + property.value!!.getPriceNBR()
        }
    }

    private fun proVisibility() = property.value!!.owner.isPro()


    fun onLikeClicked() {
        showBlockProgressBar()
        val action = if (property.value!!.isFavorite) {
            REMOVE
        } else {
            ADD
        }
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.addRemoveFavorite(FavoriteRequest(property.value!!.id, action))
                }
                onLikeClickedSuccess(response)
            }, { error ->
                onLikeClickedError(error)
            })
        }
    }

    private fun onLikeClickedSuccess(response: GlobalResponse) {
        isFavourite.value = isFavourite.value!!.not()
        hideBlockProgressBar()
    }

    private fun onLikeClickedError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }


    fun onShareClicked() {
        navigate(Navigation.ShareNavigation(property.value!!))
    }


    fun onMeetClicked() {
        //TODO: scroll to calendar
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

}