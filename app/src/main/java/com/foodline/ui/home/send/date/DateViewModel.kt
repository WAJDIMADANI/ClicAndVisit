package com.foodline.ui.home.send.date

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.foodline.base.BaseAndroidViewModel
import com.foodline.global.listener.SchedulerProvider
import com.foodline.global.utils.getDateWithPattern
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject


@HiltViewModel
class DateViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle

) : BaseAndroidViewModel(application, schedulerProvider) {

    private var calendar = Calendar.getInstance()

    val date: MutableLiveData<String> = MutableLiveData()


    init {

    }

    fun setSelectTime(calendar: Calendar) {
        this.calendar = calendar
        date.value = getDateWithPattern(calendar.timeInMillis)
    }

}