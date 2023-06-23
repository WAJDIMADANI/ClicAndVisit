package com.clickandvisit.ui.ads.addads.stepone

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.data.model.property.add.PropertyAdd
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.isWhiteSpaces
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

    fun validateFields(): Boolean {
        return validSurface() and
                validPriceTo() and
                isChecked1() and
                isChecked2() and
                isChecked3()
    }

    private fun validSurface() =
        !surface.value.isWhiteSpaces()

    private fun validPriceTo() =
        !price.value.isWhiteSpaces()

    private fun isChecked1(): Boolean {
        return checkedSale.value!! || checkedRent.value!!
    }

    private fun isChecked2(): Boolean {
        return checkedHome.value!! || checkedB.value!! || checkedApp.value!! || checkedTer.value!! || checkedGarage.value!! || checkedComm.value!!
    }

    private fun isChecked3(): Boolean {
        return checked1.value!! || checked2.value!! || checked3.value!! || checked4.value!! || checked5.value!! || checkedNA.value!!
    }

    fun onDPEAClicked() {

    }

    fun onEditProperty(property: Property) {
        surface.value = property.surface
        price.value = property.price
        stage.value = property.stage
        on.value = property.stageS
        info.value = property.otherInfo
    }

}