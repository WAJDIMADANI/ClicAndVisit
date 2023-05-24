package com.foodline.ui.menu

import android.app.Application
import com.foodline.base.BaseAndroidViewModel
import com.foodline.data.repository.abs.UserRepository
import com.foodline.global.helper.Navigation
import com.foodline.global.listener.SchedulerProvider
import com.foodline.global.listener.ToolBarListener
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