package com.clickandvisit.ui.ads.favourites

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.GlobalResponse
import com.clickandvisit.data.model.property.*
import com.clickandvisit.data.model.reservation.ReserveResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.OnPropertyClickedListener
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.tryCatch
import com.clickandvisit.ui.ads.adsdetails.CalendarUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener,
    OnPropertyClickedListener {

    val list: MutableLiveData<List<Property>> = MutableLiveData(arrayListOf())

    var searchRequest = SearchRequest(sortBy = "date", sortHow = "desc")


    init {
        getSearch()
    }

    fun getUserId(): Int {
        return userRepository.getCurrentUserId()
    }

    private fun getSearch() {
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.favoriteList()
                }
                onGetSearchSuccess(response)
            }, { error ->
                onGetSearchError(error)
            })
        }
    }


    private fun onGetSearchSuccess(response: FavoritesResponse) {
        hideBlockProgressBar()
        list.value = response.properties
    }

    private fun onGetSearchError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onVisitNowClicked(property: Property) {

        val now = Calendar.getInstance()
        now.set(Calendar.MINUTE, 0)
        val sdf = SimpleDateFormat("HH:mm")

        shownChoseDialog(
            title = null,
            message = "Voulez-vous confirmer votre RDV pour le ${
                CalendarUtils.formattedDate(
                    LocalDate.now()
                )
            } à ${sdf.format(now.time)} ?",
            "Oui",
            "Non",
            okActionBlock = {
                viewModelScope.launch {
                    tryCatch({
                        val response = withContext(schedulerProvider.dispatchersIO()) {
                            userRepository.reserve(
                                property.id,
                                "${
                                    CalendarUtils.getWsFormattedDate(
                                        LocalDate.now()
                                    )
                                } ${sdf.format(now.time)}"
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

    }


    override fun onItemClicked(value: Property) {
        navigate(Navigation.AdsDetailsActivityNavigation(value))
    }

    override fun onLikeClicked(value: Property) {
        showBlockProgressBar()
        val action = if (value.isFavorite) {
            REMOVE
        } else {
            ADD
        }
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.addRemoveFavorite(FavoriteRequest(value.id, action))
                }
                onLikeClickedSuccess(response)
            }, { error ->
                onLikeClickedError(error)
            })
        }
    }

    private fun onLikeClickedSuccess(response: GlobalResponse) {
        hideBlockProgressBar()
        getSearch()
    }

    private fun onLikeClickedError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }


    override fun onShareClicked(value: Property) {
        navigate(Navigation.ShareNavigation(value))
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

}