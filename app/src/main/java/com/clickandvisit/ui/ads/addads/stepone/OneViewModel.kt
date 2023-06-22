package com.clickandvisit.ui.ads.addads.stepone

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.add.PropertyAdd
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.listener.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OneViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider) {

    /** Radio buttons states**/
    val checkedSale: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedRent: MutableLiveData<Boolean> = MutableLiveData(false)

    val checkedHome: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedB: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedApp: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedTer: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedGarage: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedComm: MutableLiveData<Boolean> = MutableLiveData(false)


    val checked1: MutableLiveData<Boolean> = MutableLiveData(false)
    val checked2: MutableLiveData<Boolean> = MutableLiveData(false)
    val checked3: MutableLiveData<Boolean> = MutableLiveData(false)
    val checked4: MutableLiveData<Boolean> = MutableLiveData(false)
    val checked5: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedNA: MutableLiveData<Boolean> = MutableLiveData(false)

    val surface: MutableLiveData<String> = MutableLiveData()
    val price: MutableLiveData<String> = MutableLiveData()
    val stage: MutableLiveData<String> = MutableLiveData()
    val on: MutableLiveData<String> = MutableLiveData()
    val info: MutableLiveData<String> = MutableLiveData()


    init {

    }

    fun onDPEAClicked() {

    }

    fun onEditProperty(property: PropertyAdd) {
        // surface.value = property.propSurface ...
    }

}