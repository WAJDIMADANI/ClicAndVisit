package com.clickandvisit.ui.home

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.TAG
import com.clickandvisit.global.utils.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by SAzouzi on 06/02/2020
 */

@HiltViewModel
class HomeViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
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
        shownChoseDialog(
            null,
            R.string.profile_sign_out_msg,
            R.string.global_yes,
            R.string.global_no,
            okActionBlock = {
                viewModelScope.launch {
                    tryCatch({
                        withContext(schedulerProvider.dispatchersIO()) {
                            userRepository.logout()
                        }
                        navigate(Navigation.SignInActivityNavigation)
                    }, { error ->
                        navigate(Navigation.SignInActivityNavigation)
                    })
                }
            }, dismissActionBlock = null
        )
    }


}