package com.clickandvisit.ui.user.splash

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.enumeration.Optional
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.global.utils.Push
import com.clickandvisit.global.utils.SPLASH_TIME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider) {

    private var notifKey: String?

    init {
        notifKey =
            savedStateHandle.get<String>(ExtraKeys.HomeNotificationKeys.HOME_NOTIFICATION_EXTRA_KEY)

        DebugLog.i("NOTIFICATION_KEY/splash", notifKey.toString())

        /*when (notifKey) {
            Push.NOTIFICATION_VAL_VISIT -> {
                DebugLog.i("NOTIFICATION_KEY/when", "setVisits(true)")
                userRepository.setVisits(true)
            }

            Push.NOTIFICATION_VAL_MEET -> {
                DebugLog.i("NOTIFICATION_KEY/when", "setMeet(true)")
                userRepository.setMeet(true)
            }

            Push.NOTIFICATION_VAL_CHAT -> {
                DebugLog.i("NOTIFICATION_KEY/when", "setChat(true)")
                userRepository.setChat(true)
            }
        }*/

        checkLoginStatus()
    }

    @UiThread
    private fun checkLoginStatus() {
        viewModelScope.launch {
            val optional = withContext(schedulerProvider.dispatchersIO()) {
                userRepository.isLoggedInWithDelay(SPLASH_TIME)
            }
            when (optional) {
                is Optional.Some -> {

                    if (notifKey.isNullOrEmpty().not()) {
                        DebugLog.i("NOTIFICATION_KEY/check", notifKey!!)
                        navigate(Navigation.HomeActivityNav(notifKey!!))
                    } else {
                        DebugLog.i("NOTIFICATION_KEY/check", "else")
                        navigate(Navigation.HomeActivityNavigation)
                    }
                }
                is Optional.None -> {
                    navigate(Navigation.SignInActivityNavigation)
                }
            }
        }
    }

}
