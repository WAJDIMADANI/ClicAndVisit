package com.clickandvisit.ui.user.splash

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.enumeration.Optional
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.ExtraKeys
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
                        navigate(Navigation.HomeActivityNav(notifKey!!))
                    } else {
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
