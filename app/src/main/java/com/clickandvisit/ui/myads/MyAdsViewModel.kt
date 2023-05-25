package com.clickandvisit.ui.myads

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MyAdsViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle
) :
    BaseAndroidViewModel(application, schedulerProvider) {

    val search: MutableLiveData<String> = MutableLiveData()


    init {

    }

    fun onBackClick(){
        navigate(Navigation.Back)
    }

    fun onFilterClick(){

    }

}
