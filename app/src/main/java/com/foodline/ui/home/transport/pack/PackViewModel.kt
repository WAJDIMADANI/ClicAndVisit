package com.foodline.ui.home.transport.pack

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.foodline.R
import com.foodline.base.BaseAndroidViewModel
import com.foodline.global.listener.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PackViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle

) : BaseAndroidViewModel(application, schedulerProvider) {

    val weightValue = MutableLiveData(application.getString(R.string.package_details_weight_value))
    val priceValue = MutableLiveData(application.getString(R.string.package_details_euro))


    init {

    }


    fun onWValueChanged(value: Float) {
        weightValue.value =
            "${value.toInt()} ${applicationContext.getString(R.string.package_details_weight_value)}"
    }

    fun onPriceValueChanged(value: Float) {
        priceValue.value =
            "${value.toInt()} ${applicationContext.getString(R.string.package_details_euro)}"
    }

}