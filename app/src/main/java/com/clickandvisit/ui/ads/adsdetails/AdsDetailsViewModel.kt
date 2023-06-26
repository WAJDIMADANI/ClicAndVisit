package com.clickandvisit.ui.ads.adsdetails;

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.utils.ExtraKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdsDetailsViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle
) : BaseAndroidViewModel(application, schedulerProvider) {


    val property = MutableLiveData<Property>()

    val imgCount = MutableLiveData<String>()

    val adsName = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val rooms = MutableLiveData<String>()
    val surface = MutableLiveData<String>()

    val checkedPro: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        property.value =
            savedStateHandle.getLiveData<Property>(ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_PROP).value

        imgCount.value = if (property.value!!.mainPhoto.isNullOrEmpty().not()) {
            property.value!!.album.size.plus(1).toString()
        } else {
            property.value!!.album.size.toString()
        }

        checkedPro.value = proVisibility()
        adsName.value = property.value!!.title.toUpperCase()

        getPrice()

        rooms.value = property.value!!.details.getRoomsNBRDetails()
        surface.value = property.value!!.surface + "\n" + applicationContext.getString(
            R.string.home_details_m_square
        )
    }

    private fun getPrice() {
        if (property.value!!.category.isEmpty() && property.value!!.price.isEmpty()) {
            price.value = ""
        } else if (property.value!!.category.isEmpty().not() && property.value!!.price.isEmpty()) {
            price.value = property.value!!.category
        } else if (property.value!!.category.isEmpty() && property.value!!.price.isEmpty().not()) {
            price.value = property.value!!.getPriceNBR()
        } else {
            price.value = property.value!!.getCategories() + property.value!!.getPriceNBR()
        }
    }

    private fun proVisibility() = property.value!!.owner.isPro()


    fun onMeetClicked() {

    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

}