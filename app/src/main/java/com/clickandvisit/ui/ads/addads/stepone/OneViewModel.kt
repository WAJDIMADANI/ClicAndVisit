package com.clickandvisit.ui.ads.addads.stepone

import android.app.Application
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
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


    val ges: MutableLiveData<String> = MutableLiveData()
    val dpe: MutableLiveData<String> = MutableLiveData()


    init {
        checked1.observeForever {
            if (it == true) {
                checked2.value = false
                checked3.value = false
                checked4.value = false
                checked5.value = false
                checkedNA.value = false
            }
        }
        checked2.observeForever {
            if (it == true) {
                checked1.value = false
                checked3.value = false
                checked4.value = false
                checked5.value = false
                checkedNA.value = false
            }
        }
        checked3.observeForever {
            if (it == true) {
                checked1.value = false
                checked2.value = false
                checked4.value = false
                checked5.value = false
                checkedNA.value = false
            }
        }
        checked4.observeForever {
            if (it == true) {
                checked1.value = false
                checked2.value = false
                checked3.value = false
                checked5.value = false
                checkedNA.value = false
            }
        }
        checked5.observeForever {
            if (it == true) {
                checked1.value = false
                checked2.value = false
                checked3.value = false
                checked4.value = false
                checkedNA.value = false
            }
        }
        checkedNA.observeForever {
            if (it == true) {
                checked1.value = false
                checked2.value = false
                checked3.value = false
                checked4.value = false
                checked5.value = false
            }
        }

        checkedHome.observeForever {
            if (it == true) {
                checkedB.value = false
                checkedApp.value = false
                checkedTer.value = false
                checkedGarage.value = false
                checkedComm.value = false
            }
        }
        checkedB.observeForever {
            if (it == true) {
                checkedHome.value = false
                checkedApp.value = false
                checkedTer.value = false
                checkedGarage.value = false
                checkedComm.value = false
            }
        }
        checkedApp.observeForever {
            if (it == true) {
                checkedHome.value = false
                checkedB.value = false
                checkedTer.value = false
                checkedGarage.value = false
                checkedComm.value = false
            }
        }
        checkedTer.observeForever {
            if (it == true) {
                checkedHome.value = false
                checkedB.value = false
                checkedApp.value = false
                checkedGarage.value = false
                checkedComm.value = false
            }
        }
        checkedGarage.observeForever {
            if (it == true) {
                checkedHome.value = false
                checkedB.value = false
                checkedApp.value = false
                checkedTer.value = false
                checkedComm.value = false
            }
        }
        checkedComm.observeForever {
            if (it == true) {
                checkedHome.value = false
                checkedB.value = false
                checkedApp.value = false
                checkedTer.value = false
                checkedGarage.value = false
            }
        }

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

    fun onDPEAClicked(value: Int) {

    }

    private fun fetchDPE(property: Property) {
        if (property.energy.isNotEmpty()) {
            navigate(Navigation.DPENavigation(property.energy))
        }
    }

    private fun fetchGES(property: Property) {
        Handler().postDelayed({
            if (property.ges.isNotEmpty()) {
                navigate(Navigation.GESNavigation(property.ges))
            }
        }, 500)
    }


    fun onEditProperty(property: Property) {
        surface.value = property.surface
        price.value = property.price.replace(" ", "")
        stage.value = property.stage
        on.value = property.stageS
        info.value = property.otherInfo
        ges.value = property.ges.ifEmpty { "" }
        dpe.value = property.energy.ifEmpty { "" }


        fetchDPE(property)
        fetchGES(property)

        if (property.type == "Vente") {
            checkedSale.value = true
        } else if (property.type == "Location") {
            checkedRent.value = true
        }


        when (property.category) {
            "Maison" -> {
                checkedHome.value = true
            }
            "Bureau" -> {
                checkedB.value = true
            }
            "Appartement" -> {
                checkedApp.value = true
            }
            "Terrain" -> {
                checkedTer.value = true
            }
            "Garage" -> {
                checkedGarage.value = true
            }
            "Commerce" -> {
                checkedComm.value = true
            }
        }

        when (property.details.pieces) {
            "1" -> {
                checked1.value = true
            }
            "2" -> {
                checked2.value = true
            }
            "3" -> {
                checked3.value = true
            }
            "4" -> {
                checked4.value = true
            }
            "5" -> {
                checked5.value = true
            }
            "NA" -> {
                checkedNA.value = true
            }
            else -> {
                checkedNA.value = true
            }
        }

    }

}