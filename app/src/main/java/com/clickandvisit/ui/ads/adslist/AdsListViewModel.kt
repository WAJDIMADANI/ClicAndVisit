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

    val list: MutableLiveData<List<Property>> = MutableLiveData(arrayListOf())
    val name1: MutableLiveData<String> = MutableLiveData(application.getString(R.string.my_ads_current))
    val name0: MutableLiveData<String> = MutableLiveData(application.getString(R.string.my_ads_validation))

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
        }
    }

    private fun onGetDiscussionError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }

    fun onBackClick() {
        navigate(Navigation.Back)
    }


    fun onInlineClick() {
        getMyProperty(1)
    }

    fun onInholdClick() {
        getMyProperty(0)
    }

    override fun onItemClicked(value: Property) {
        navigate(Navigation.AdsDetailsActivityNavigation(value))
    }

    override fun onShareClicked(value: Property) {
        navigate(Navigation.ShareNavigation(value))
    }

    override fun onEditClicked(value: Property) {
        navigate(Navigation.AddAdsActivity(value))
    }

    override fun onRateClicked(value: Property) {
        navigate(Navigation.RateNav)
    }

    override fun onMeetClicked(value: Property) {
        DebugLog.i(TAG, "onMeetClicked")
    }

}