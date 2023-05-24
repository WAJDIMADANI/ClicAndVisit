package com.foodline.ui.intro

import android.app.Application
import com.foodline.base.BaseAndroidViewModel
import com.foodline.data.repository.abs.UserRepository
import com.foodline.global.listener.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class IntroViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider) {

    init {
        viewModelScope.launch {
            withContext(schedulerProvider.dispatchersIO()) {
                userRepository.setIntro()
            }
        }
    }
}