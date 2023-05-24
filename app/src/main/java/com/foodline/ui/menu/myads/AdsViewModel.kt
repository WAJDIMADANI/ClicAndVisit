package com.foodline.ui.menu.myads

import android.app.Application
import com.foodline.base.BaseAndroidViewModel
import com.foodline.data.repository.abs.UserRepository
import com.foodline.global.helper.Navigation
import com.foodline.global.listener.SchedulerProvider
import com.foodline.global.listener.ToolBarListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AdsViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {


    init {

    }


    fun onBackClick() {
        navigate(Navigation.Back)
    }

    fun onFilterClick() {
        navigate(Navigation.FilterActivityNavigation)
    }

    fun itemClick() {
        navigate(Navigation.DetailsActivityNavigation)
    }

}