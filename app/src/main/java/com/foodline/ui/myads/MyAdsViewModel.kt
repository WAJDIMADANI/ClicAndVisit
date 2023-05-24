package com.foodline.ui.myads

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.foodline.base.BaseAndroidViewModel
import com.foodline.global.helper.Navigation
import com.foodline.global.listener.SchedulerProvider
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
