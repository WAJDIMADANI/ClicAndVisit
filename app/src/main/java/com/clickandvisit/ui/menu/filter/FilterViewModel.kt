package com.clickandvisit.ui.menu.filter

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.getDateWithPattern
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject


@HiltViewModel
class FilterViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) :
    BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {

    private var calendar = Calendar.getInstance()

    val date: MutableLiveData<String> = MutableLiveData()

    val weightValue = MutableLiveData(application.getString(R.string.package_details_weight_value))
    val priceValue = MutableLiveData(application.getString(R.string.package_details_euro))

    init {

    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

    fun setSelectTime(calendar: Calendar) {
        this.calendar = calendar
        date.value = getDateWithPattern(calendar.timeInMillis)
    }

    fun onApplyClick() {

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
