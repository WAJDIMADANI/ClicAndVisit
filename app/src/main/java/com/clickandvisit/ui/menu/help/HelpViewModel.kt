package com.clickandvisit.ui.menu.help

import android.app.Application
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
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