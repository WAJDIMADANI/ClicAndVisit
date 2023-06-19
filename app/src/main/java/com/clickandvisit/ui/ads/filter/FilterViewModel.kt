package com.clickandvisit.ui.ads.filter

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.SearchRequest
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.ExtraKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FilterViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {

    val searchRequest: MutableLiveData<SearchRequest> = MutableLiveData()

    val city: MutableLiveData<String> = MutableLiveData()

    init {
        searchRequest.value =
            savedStateHandle.getLiveData<SearchRequest>(ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY).value
    }

    fun onConfirmClicked() {
        searchRequest.value?.address = city.value
        
        navigate(Navigation.HomeActivityNavigation)
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

}