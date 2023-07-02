package com.clickandvisit.ui.ads.favourites

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.GlobalResponse
import com.clickandvisit.data.model.property.*
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.OnPropertyClickedListener
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener, OnPropertyClickedListener{

    val list: MutableLiveData<List<Property>> = MutableLiveData(arrayListOf())

    var searchRequest = SearchRequest(sortBy = "date", sortHow = "desc")


    init {
        getSearch()
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