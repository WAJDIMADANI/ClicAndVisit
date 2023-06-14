package com.clickandvisit.ui.home

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by SAzouzi on 06/02/2020
 */

@HiltViewModel
class HomeViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle
) :
    BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {

    init {

    }

    override fun onBMenuClicked() {
        navigate(Navigation.OpenDrawerNavigation)
    }

    override fun onSearchClicked() {
        navigate(Navigation.SearchActivityNavigation)
    }

    override fun onChatClicked() {
        navigate(Navigation.ChatActivityNavigation)
    }

    override fun onProfileClicked() {
        navigate(Navigation.ProfileActivityNavigation)
    }

    fun disconnect() {
 /*       showChoseDialog(
            messageId = R.string.global_home_logout_txt,
            okId = R.string.global_yes,
            cancelId = R.string.global_no,
            okActionBlock = {
                userRepository.disconnect()
                navigate(Navigation.FirstScreenActivityNavigation)
            },
            dismissActionBlock = null
        )*/
    }


}