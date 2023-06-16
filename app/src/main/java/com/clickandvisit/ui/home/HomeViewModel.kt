package com.clickandvisit.ui.home

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
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

    fun isConnected(): Boolean {
        return userRepository.isConnected()
    }

    override fun onSearchClicked() {
        if (userRepository.isConnected()) {
            navigate(Navigation.SearchActivityNavigation)
        } else {
            navigate(Navigation.SignInActivityNavigation)
        }
    }

    override fun onChatClicked() {
        if (userRepository.isConnected()) {
            navigate(Navigation.ChatActivityNavigation)
        } else {
            navigate(Navigation.SignInActivityNavigation)
        }
    }

    override fun onProfileClicked() {
        if (userRepository.isConnected()) {
            navigate(Navigation.ProfileActivityNavigation)
        } else {
            navigate(Navigation.SignInActivityNavigation)
        }
    }

    fun disconnect() {
        //TODO is user not connected -> invisible
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