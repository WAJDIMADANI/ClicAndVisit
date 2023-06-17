package com.clickandvisit.ui.home.map

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.listener.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MapsViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) :
    BaseAndroidViewModel(application, schedulerProvider) {

    init {

    }

    fun onMapsClicked(){

    }
}
