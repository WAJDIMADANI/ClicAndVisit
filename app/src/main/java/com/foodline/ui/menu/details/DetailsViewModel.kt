package com.foodline.ui.menu.details

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.foodline.base.BaseAndroidViewModel
import com.foodline.data.repository.abs.UserRepository
import com.foodline.global.helper.Navigation
import com.foodline.global.listener.SchedulerProvider
import com.foodline.global.listener.ToolBarListener
import com.foodline.global.utils.ExtraKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {

    val isFromPack: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFromAds: MutableLiveData<Boolean> = MutableLiveData(false)


    init {
        isFromPack.value =
            savedStateHandle.getLiveData<Boolean>(ExtraKeys.DetailsActivity.DETAILS_EXTRA_KEY_FROM_PACK).value
        isFromAds.value =
            savedStateHandle.getLiveData<Boolean>(ExtraKeys.DetailsActivity.DETAILS_EXTRA_KEY_FROM_ADS).value
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    fun onChatClick() {

    }

}