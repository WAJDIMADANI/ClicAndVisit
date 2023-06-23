package com.clickandvisit.ui.ads.adslist

import android.app.Application
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.OnMyPropertyClickedListener
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AdsListViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener,
    OnMyPropertyClickedListener {


    init {

    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    override fun onItemClicked() {
        TODO("Not yet implemented")
    }

    override fun onShareClicked() {
        TODO("Not yet implemented")
    }

    override fun onEditClicked() {
        TODO("Not yet implemented")
    }

    override fun onRateClicked() {
        TODO("Not yet implemented")
    }

    override fun onMeetClicked() {
        TODO("Not yet implemented")
    }

}