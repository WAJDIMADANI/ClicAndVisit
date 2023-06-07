package com.clickandvisit.ui.ads.adsdetails;

import android.app.Application;

import androidx.lifecycle.SavedStateHandle;
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.global.helper.Navigation

import com.clickandvisit.global.listener.SchedulerProvider;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
class AdsDetailsViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle
) : BaseAndroidViewModel(application, schedulerProvider) {

    init {

    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

}