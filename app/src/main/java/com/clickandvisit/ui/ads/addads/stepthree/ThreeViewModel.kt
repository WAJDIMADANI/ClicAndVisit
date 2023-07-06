package com.clickandvisit.ui.ads.addads.stepthree

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.isWhiteSpaces
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ThreeViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle

) : BaseAndroidViewModel(application, schedulerProvider) {

    val city = MutableLiveData<String>()
    val postalCode = MutableLiveData<String>()
    val address = MutableLiveData<String>()

    val inter = MutableLiveData<String>()
    val interCode = MutableLiveData<String>()
    val otherInfo = MutableLiveData<String>()


    init {

    }


    fun validateFields(): Boolean {
        return validCity() and
                validPostalCodeTo() and
                validAddress()
    }

    private fun validCity() =
        !city.value.isWhiteSpaces()

    private fun validPostalCodeTo()=
        !postalCode.value.isWhiteSpaces()

    private fun validAddress()=
        !address.value.isWhiteSpaces()


    fun onEditProperty(property: Property) {
        city.value = property.city
        postalCode.value = property.postalCode
        address.value = property.road
        inter.value = property.interphone
        interCode.value = property.portail
        otherInfo.value = property.otherInfo
    }

}