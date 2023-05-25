package com.clickandvisit.ui.splash

import android.app.Application
import androidx.annotation.UiThread
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.enumeration.Optional
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
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
    private val userRepository: UserRepository,
    versionValue: String
) : BaseAndroidViewModel(application, schedulerProvider) {

    init {
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
                    navigate(Navigation.HomeActivityNavigation)
                }
                is Optional.None -> {
                    navigate(Navigation.SignInActivityNavigation)
                }
            }
        }
    }

}
