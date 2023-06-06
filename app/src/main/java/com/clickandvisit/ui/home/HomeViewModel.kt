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
        DebugLog.d(TAG, "onBMenuClicked")
        navigate(Navigation.OpenDrawerNavigation)
    }

    override fun onSearchClicked() {
        DebugLog.d(TAG, "onSearchClicked")
    }

    override fun onChatClicked() {
        DebugLog.d(TAG, "onChatClicked")
    }

    override fun onProfileClicked() {
        DebugLog.d(TAG, "onProfileClicked")
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