package com.foodline.ui.menu.help

import android.app.Application
import com.foodline.base.BaseAndroidViewModel
import com.foodline.data.repository.abs.UserRepository
import com.foodline.global.helper.Navigation
import com.foodline.global.listener.SchedulerProvider
import com.foodline.global.listener.ToolBarListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HelpViewModel
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

    fun onImageBack() {

    }

    fun onHomeClick() {

    }

    fun onPackageClick() {

    }

    fun onProfileClick() {

    }

    fun onAdsClick() {

    }

    fun onPeymentClick() {

    }

    fun onChatClick() {

    }

    fun onParamClick() {

    }

}