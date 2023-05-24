package com.foodline.ui.home.transport.info

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.foodline.base.BaseAndroidViewModel
import com.foodline.global.listener.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class InfoViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle

) : BaseAndroidViewModel(application, schedulerProvider) {

    init {

    }

}