package com.clickandvisit.ui.ads.filter

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.SearchRequest
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.ExtraKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FilterViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener {

    val searchRequest: MutableLiveData<SearchRequest> = MutableLiveData()

    val city: MutableLiveData<String> = MutableLiveData()
    val priceMin: MutableLiveData<String> = MutableLiveData()
    val priceMax: MutableLiveData<String> = MutableLiveData()
    val surfaceMin: MutableLiveData<String> = MutableLiveData()
    val surfaceMax: MutableLiveData<String> = MutableLiveData()


    val piecesList: MutableLiveData<List<String>> = MutableLiveData()
    val minSelectedItem = MutableLiveData<String>()
    val maxSelectedItem = MutableLiveData<String>()

    /** Radio buttons states**/
    val checkedSale: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedRent: MutableLiveData<Boolean> = MutableLiveData(false)

    val checkedHome: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedB: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedApp: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedTer: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedGarage: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkedComm: MutableLiveData<Boolean> = MutableLiveData(false)


    init {
        searchRequest.value =
            savedStateHandle.getLiveData<SearchRequest>(ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY).value
/*        city.value = searchRequest.value?.address
        priceMin.value = if (searchRequest.value?.minPrice!! >= 0) {
            searchRequest.value?.minPrice.toString()
        } else {
            ""
        }
        priceMax.value = searchRequest.value?.maxPrice.toString()
        surfaceMin.value = searchRequest.value?.minArea.toString()
        surfaceMax.value = searchRequest.value?.maxArea.toString()*/

        piecesList.value = applicationContext.resources.getStringArray(R.array.pieces).asList()
    }

    fun onConfirmClicked() {
        searchRequest.value?.address = city.value
        searchRequest.value?.minPrice = priceMin.value?.toInt()
        searchRequest.value?.maxPrice = priceMax.value?.toInt()
        searchRequest.value?.minArea = surfaceMin.value?.toInt()
        searchRequest.value?.maxArea = surfaceMax.value?.toInt()
        searchRequest.value?.minRooms = minSelectedItem.value
        searchRequest.value?.maxRooms = maxSelectedItem.value


        searchRequest.value?.adsType = if (checkedSale.value == true) {
            30
        } else if (checkedRent.value == true) {
            29
        } else {
            null
        }

        val categoryList = ArrayList<Int>()
        if (checkedHome.value == true) {
            categoryList.add(96)
        }
        if (checkedB.value == true) {
            categoryList.add(99)
        }
        if (checkedApp.value == true) {
            categoryList.add(97)
        }
        if (checkedTer.value == true) {
            categoryList.add(100)
        }
        if (checkedGarage.value == true) {
            categoryList.add(98)
        }
        if (checkedComm.value == true) {
            categoryList.add(101)
        }

        searchRequest.value?.category = categoryList

        navigate(Navigation.HomeActivityNavigation)
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }

}