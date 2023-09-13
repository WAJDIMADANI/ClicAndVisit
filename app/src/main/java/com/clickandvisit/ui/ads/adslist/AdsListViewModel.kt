package com.clickandvisit.ui.ads.adslist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.data.model.property.SearchResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.OnMyPropertyClickedListener
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.TAG
import com.clickandvisit.global.utils.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class AdsListViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener,
    OnMyPropertyClickedListener {

    val list: MutableLiveData<ArrayList<Property>> = MutableLiveData(arrayListOf())
    val name1: MutableLiveData<String> =
        MutableLiveData(application.getString(R.string.my_ads_current))
    val name0: MutableLiveData<String> =
        MutableLiveData(application.getString(R.string.my_ads_validation))

    val inHold = MutableLiveData(false)

    lateinit var searchResponse: SearchResponse

    init {

    }


    fun onResume() {
        getMyProperty(1)
    }

    private fun getMyProperty(statusCode: Int) {
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getMyProperty()
                }
                onGetDiscussionSuccess(response, statusCode)
            }, { error ->
                onGetDiscussionError(error)
            })
        }
    }

    private fun onGetDiscussionSuccess(response: SearchResponse, statusCode: Int) {
        hideBlockProgressBar()
        searchResponse = response
        list.value = response.properties.filter {
            it.statusCode == statusCode
        } as ArrayList
    }

    private fun onGetDiscussionError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }


    fun onInlineClick() {
        inHold.value = false
        getMyProperty(1)
    }

    fun onInholdClick() {
        inHold.value = true
        getMyProperty(0)
    }

    override fun onItemClicked(value: Property) {
        navigate(Navigation.AdsDetailsActivityNavigation(value))
    }

    override fun onShareClicked(value: Property) {
        navigate(Navigation.ShareNavigation(value))
    }

    override fun onEditClicked(value: Property) {
        navigate(Navigation.AddAdsActivity(value, false))
    }

    override fun onRateClicked(value: Property) {
        navigate(Navigation.RateNav)
    }

    override fun onMeetClicked(value: Property) {
        navigate(Navigation.AddAdsActivity(value, true))
    }

    fun deleteProp(propertyId: Int, position: Int) {
        shownChoseDialog(
            null,
            R.string.delete_prop_msg,
            R.string.global_yes,
            R.string.global_no,
            okActionBlock = {
                viewModelScope.launch {
                    tryCatch({
                        withContext(schedulerProvider.dispatchersIO()) {
                            userRepository.enableDisableProperty(propertyId)
                        }
                        list.value?.removeAt(position)
                        onResume()
                    }, { error ->
                        onResume()
                    })
                }
            }, dismissActionBlock = {
                onResume()
            }
        )
    }

}