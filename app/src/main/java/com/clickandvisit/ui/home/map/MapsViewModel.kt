package com.clickandvisit.ui.home.map

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.SearchResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.ExtraKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MapsViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : BaseAndroidViewModel(application, schedulerProvider) {


    val searchResponse = MutableLiveData<SearchResponse>()

    init {
        searchResponse.value =
            savedStateHandle.getLiveData<SearchResponse>(ExtraKeys.MapsActivity.SEARCH_EXTRA_KEY).value
    }

    fun onMapsClicked() {
        navigate(Navigation.Back)
    }
}
