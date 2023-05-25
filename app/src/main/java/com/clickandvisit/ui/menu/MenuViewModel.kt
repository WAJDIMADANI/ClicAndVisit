package com.clickandvisit.ui.menu

import android.app.Application
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MenuViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {


    init {

    }

    fun onAdsClick() {
        navigate(Navigation.AdsActivityNavigation)
    }

    fun onPackageClick() {
        navigate(Navigation.PackageActivityNavigation)
    }

    fun onProfileClick() {
        navigate(Navigation.ProfileActivityNavigation)
    }

    fun onAlertsClick() {
        navigate(Navigation.AlertsActivityNavigation)
    }

    fun onPaymentClick() {
        navigate(Navigation.PaymentActivityNavigation)
    }

    fun onChatClick() {
        navigate(Navigation.ChatActivityNavigation)
    }

    fun onParamClick() {
        navigate(Navigation.HelpActivityNavigation)
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }
}