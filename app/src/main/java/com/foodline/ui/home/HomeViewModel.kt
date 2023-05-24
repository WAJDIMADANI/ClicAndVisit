package com.foodline.ui.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.foodline.base.BaseAndroidViewModel
import com.foodline.global.helper.Navigation
import com.foodline.global.listener.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle
) :
    BaseAndroidViewModel(application, schedulerProvider) {

    val search: MutableLiveData<String> = MutableLiveData()


    init {

    }

    fun onMenuClick() {
        navigate(Navigation.MenuActivityNavigation)
    }

    fun onSendClick() {
        navigate(Navigation.SendActivityNavigation)
    }

    fun onTransportClick() {
        navigate(Navigation.TransportActivityNavigation)
    }

}