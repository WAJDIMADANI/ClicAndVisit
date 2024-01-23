package com.clickandvisit.ui.ads.adsdetails;

import android.app.Application
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.GlobalResponse
import com.clickandvisit.data.model.property.ADD
import com.clickandvisit.data.model.property.FavoriteRequest
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.data.model.property.REMOVE
import com.clickandvisit.data.model.reservation.AvailabilityResponse
import com.clickandvisit.data.model.reservation.ReserveResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.global.utils.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
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
    val pieces = MutableLiveData<String>()
    val surface = MutableLiveData<String>()
    val info = MutableLiveData<String>()
    val ref = MutableLiveData<String>()
    val isFavourite = MutableLiveData<Boolean>()
    val checkedPro: MutableLiveData<Boolean> = MutableLiveData(false)
    val like: MutableLiveData<Drawable> = MutableLiveData()
    val list: MutableLiveData<List<String>> = MutableLiveData(arrayListOf())
    var roomsList = arrayListOf<String>()
    val isMyProp = MutableLiveData(false)


    val availableHours1: MutableLiveData<List<String?>?> = MutableLiveData()
    val availableHours2: MutableLiveData<List<String?>?> = MutableLiveData()
    val availableHours3: MutableLiveData<List<String?>?> = MutableLiveData()
    val availableHours4: MutableLiveData<List<String?>?> = MutableLiveData()
    val availableHours5: MutableLiveData<List<String?>?> = MutableLiveData()
    val availableHours6: MutableLiveData<List<String?>?> = MutableLiveData()
    val availableHours7: MutableLiveData<List<String?>?> = MutableLiveData()


    @RequiresApi(Build.VERSION_CODES.O)
    var firstDay = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))


    init {
        property.value =
            savedStateHandle.getLiveData<Property>(ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_PROP).value

        ref.value = "Référence : CV" + property.value!!.id.toString()

        fetchImgCount()
        getPrice()
        fetchRoomsDetails()

        fetchDPE()
        fetchGES()

        checkedPro.value = proVisibility()
        adsName.value = property.value!!.title.toUpperCase()
        pieces.value = property.value!!.details.getTPieces()
        surface.value = property.value!!.getPropertySurface() + "\n" + applicationContext.getString(
            R.string.home_details_m_square
        )
        info.value = property.value!!.otherDetails
        isFavourite.value = property.value!!.isFavorite

        if (isFavourite.value == true) {
            like.value = ContextCompat.getDrawable(applicationContext, R.drawable.ic_like_on)
        } else {
            like.value = ContextCompat.getDrawable(applicationContext, R.drawable.ic_like)
        }

        fetchAvailability1(CalendarUtils.getWsFormattedDate(firstDay))

        isMyProp.value = isCurrentUser()
    }


    fun isCurrentUser(): Boolean {
        return property.value!!.owner.id == userRepository.getSharedUser().id
    }

    fun fetchAvailability1(date: String) {
        availableHours1.value = arrayListOf()
        availableHours2.value = arrayListOf()
        availableHours3.value = arrayListOf()
        availableHours4.value = arrayListOf()
        availableHours5.value = arrayListOf()
        availableHours6.value = arrayListOf()
        availableHours7.value = arrayListOf()
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({

                var nextDay = firstDay

                var allWeekDate = date.plus(",")
                nextDay = nextDay.plusDays(1)
                allWeekDate = allWeekDate.plus(nextDay).plus(",")
                nextDay = nextDay.plusDays(1)
                allWeekDate = allWeekDate.plus(nextDay).plus(",")
                nextDay = nextDay.plusDays(1)
                allWeekDate = allWeekDate.plus(nextDay).plus(",")
                nextDay = nextDay.plusDays(1)
                allWeekDate = allWeekDate.plus(nextDay).plus(",")
                nextDay = nextDay.plusDays(1)
                allWeekDate = allWeekDate.plus(nextDay).plus(",")
                nextDay = nextDay.plusDays(1)
                allWeekDate = allWeekDate.plus(nextDay)

                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getAvailability(allWeekDate, property.value!!.id)
                }
                getAvailabilitySuccess(response)
            }, { error ->
                onLikeClickedError(error)
            })
        }
    }

    private fun getAvailabilitySuccess(response: AvailabilityResponse) {
        hideBlockProgressBar()
        availableHours1.value = response.availableHours[0]
        availableHours2.value = response.availableHours[1]
        availableHours3.value = response.availableHours[2]
        availableHours4.value = response.availableHours[3]
        availableHours5.value = response.availableHours[4]
        availableHours6.value = response.availableHours[5]
        availableHours7.value = response.availableHours[6]
    }

    private fun fetchDPE() {
        if (property.value!!.energy.isNotEmpty()) {
            navigate(Navigation.DPENavigation(property.value!!.energy))
        }
    }

    private fun fetchGES() {
        Handler().postDelayed({
            if (property.value!!.ges.isNotEmpty()) {
                navigate(Navigation.GESNavigation(property.value!!.ges))
            }
        }, 500)
    }

    private fun fetchImgCount() {
        imgCount.value = if (property.value!!.mainPhoto.isNullOrEmpty().not()) {

            if (property.value!!.album.isNullOrEmpty()) {
                "1"
            } else {
                property.value!!.album?.size?.plus(1).toString()
            }

        } else {
            property.value!!.album?.size.toString()
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
            roomsList.add(applicationContext.getString(R.string.ad_ads_131))
        }

        if (property.value!!.details.piscinable.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_141))
        }

        if (property.value!!.details.poolHouse.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_151))
        }

        if (property.value!!.details.sansVisAVis.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_161))
        }

        if (property.value!!.details.ascenseur.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_171))
        }

        if (property.value!!.details.duplex.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_181))
        }

        if (property.value!!.details.triplex.isNotEmpty()) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_191))
        }

        if (property.value!!.details.rezDeJardin?.isNotEmpty() == true) {
            roomsList.add(applicationContext.getString(R.string.ad_ads_201))
        }

        list.value = roomsList
    }

    private fun getPrice() {
        val cc = if ("Vente" == property.value?.type) {
            ""
        } else {
            "/mois (cc)"
        }
        if (property.value!!.category.isEmpty() && property.value!!.price.isEmpty()) {
            propPrice.value = ""
        } else if (property.value!!.category.isEmpty().not() && property.value!!.price.isEmpty()) {
            propPrice.value = property.value!!.category
        } else if (property.value!!.category.isEmpty() && property.value!!.price.isEmpty().not()) {
            propPrice.value = "${property.value!!.getPriceNBR()} $cc"
        } else {
            propPrice.value =
                "${property.value!!.getCategories()} ${property.value!!.getPriceNBR()} $cc"
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
        property.value?.isFavorite = property.value?.isFavorite!!.not()
        isFavourite.value = isFavourite.value!!.not()
        if (isFavourite.value == true) {
            like.value = ContextCompat.getDrawable(applicationContext, R.drawable.ic_like_on)
        } else {
            like.value = ContextCompat.getDrawable(applicationContext, R.drawable.ic_like)
        }
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
        navigate(Navigation.Scroll)
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    fun reserve(date: String, hour: String) {
        shownChoseDialog(
            title = null,
            message = "Voulez-vous confirmer votre RDV pour le $date à $hour ?",
            "Oui",
            "Non",
            okActionBlock = {
                viewModelScope.launch {
                    tryCatch({
                        val response = withContext(schedulerProvider.dispatchersIO()) {
                            userRepository.reserve(
                                property.value!!.id,
                                "$date $hour"
                            )
                        }
                        onReserveSuccess(response)
                    }, { error ->
                        onLikeClickedError(error)
                    })
                }
            }, dismissActionBlock = null
        )
    }

    private fun onReserveSuccess(response: ReserveResponse) {
        shownSimpleDialog(messageId = R.string.rdv_response, okActionBlock = {
            navigate(Navigation.Back)
        })
    }

}