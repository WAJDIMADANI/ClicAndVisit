package com.clickandvisit.ui.ads.search


import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.*
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.OnSearchClickedListener
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.global.utils.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener, OnSearchClickedListener {

    val list: MutableLiveData<List<SearchData>> = MutableLiveData(arrayListOf())

    val searchRequest: MutableLiveData<SearchRequest> = MutableLiveData()


    init {
        searchRequest.value =
            savedStateHandle.getLiveData<SearchRequest>(ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY).value
        getSavedSearch()
    }

    private fun getSavedSearch() {
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getSavedSearch()
                }
                onGetSavedSearchSuccess(response)
            }, { error ->
                onGetSavedSearchError(error)
            })
        }
    }

    private fun onGetSavedSearchSuccess(response: SavedSearchResponse) {
        hideBlockProgressBar()
        list.value = response.data
    }

    private fun onGetSavedSearchError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    override fun onNotifyClick(data: SearchData) {
        //TODO: query
    }

    override fun onDeleteClick(data: SearchData) {
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.deleteSearch(data.id.toInt())
                }
                getSavedSearch()
            }, { error ->
                onGetSavedSearchError(error)
            })
        }
    }

    override fun onSeeClick(data: SearchData) {
        searchRequest.value?.minArea = data.data.minArea?.toInt()
        searchRequest.value?.maxArea = data.data.maxArea?.toInt()

        searchRequest.value?.minPrice = data.data.minPrice?.toInt()
        searchRequest.value?.maxPrice = data.data.maxPrice?.toInt()

        searchRequest.value?.minRooms = data.data.minRooms
        searchRequest.value?.maxRooms = data.data.maxRooms

        searchRequest.value?.address = data.data.adresse
        searchRequest.value?.category = data.data.type

        navigate(Navigation.HomeActivityNavigation)
    }

}