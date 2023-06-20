package com.clickandvisit.ui.ads.addads.stepthree

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.global.listener.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ThreeViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle

) : BaseAndroidViewModel(application, schedulerProvider) {

    val city = MutableLiveData<String>()
    val postalCode = MutableLiveData<String>()
    val address = MutableLiveData<String>()

    val inter = MutableLiveData<String>()
    val interCode = MutableLiveData<String>()
    val info = MutableLiveData<String>()


    init {

    }

}