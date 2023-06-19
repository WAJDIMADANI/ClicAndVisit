package com.clickandvisit.ui.home

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.data.model.property.SearchRequest
import com.clickandvisit.data.model.property.SearchResponse
import com.clickandvisit.data.model.user.TokenResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.OnPropertyClickedListener
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.global.utils.TAG
import com.clickandvisit.global.utils.tryCatch
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by SAzouzi on 06/02/2020
 */

@HiltViewModel
class HomeViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) :
    BaseAndroidViewModel(application, schedulerProvider), ToolBarListener,
    OnPropertyClickedListener {

    val list: MutableLiveData<List<Property>> = MutableLiveData(arrayListOf())

    val listCount: MutableLiveData<String> = MutableLiveData()

    var searchRequest = SearchRequest(sortBy = "date", sortHow = "desc")

    lateinit var searchResponse: SearchResponse

    init {
        getSearch()
        setPushToken()
    }

    private fun getSearch() {
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.search()
                }
                onGetDiscussionSuccess(response)
            }, { error ->
                onGetDiscussionError(error)
            })
        }
    }


    private fun onGetDiscussionSuccess(response: SearchResponse) {
        hideBlockProgressBar()
        searchResponse = response
        list.value = response.properties
        listCount.value =
            "${applicationContext.getString(R.string.home_list)}(${response.properties.count()})"
    }

    private fun onGetDiscussionError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }


    private fun setPushToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { DebugLog.w("Firebase", "getInstanceId failed", it) }
                    return@OnCompleteListener
                }
                task.result?.token?.let {
                    DebugLog.i("Firebase", it)
                    viewModelScope.launch {
                        tryCatch({
                            val res = userRepository.setPushToken(it)
                            onPushTokenSuccess(res)
                        }, { error ->
                            onPushTokenError(error)
                        })
                    }

                }

            })
    }

    private fun onPushTokenSuccess(res: TokenResponse) {
        DebugLog.i(TAG, res.toString())
    }

    private fun onPushTokenError(throwable: Throwable) {
        handleThrowable(throwable)
    }

    override fun onBMenuClicked() {
        navigate(Navigation.OpenDrawerNavigation)
    }

    fun onMapsClicked() {
        navigate(Navigation.MapsActivityNavigation(searchResponse))
    }

    fun isConnected(): Boolean {
        return userRepository.isConnected()
    }

    override fun onSearchClicked() {
        if (userRepository.isConnected()) {
            navigate(Navigation.FilterActivityNavigation(searchRequest))
        } else {
            navigate(Navigation.SignInActivityNavigation)
        }
    }

    fun onFilterClicked() {
        //TODO:
    }

    override fun onChatClicked() {
        if (userRepository.isConnected()) {
            navigate(Navigation.ChatActivityNavigation)
        } else {
            navigate(Navigation.SignInActivityNavigation)
        }
    }

    override fun onProfileClicked() {
        if (userRepository.isConnected()) {
            navigate(Navigation.ProfileActivityNavigation)
        } else {
            navigate(Navigation.SignInActivityNavigation)
        }
    }

    fun disconnect() {
        //TODO is user not connected -> invisible
        shownChoseDialog(
            null,
            R.string.profile_sign_out_msg,
            R.string.global_yes,
            R.string.global_no,
            okActionBlock = {
                viewModelScope.launch {
                    tryCatch({
                        withContext(schedulerProvider.dispatchersIO()) {
                            userRepository.logout()
                        }
                        navigate(Navigation.SignInActivityNavigation)
                    }, { error ->
                        navigate(Navigation.SignInActivityNavigation)
                    })
                }
            }, dismissActionBlock = null
        )
    }

    override fun onItemClicked(value: Property) {
        DebugLog.i(TAG, value.toString())
    }

    override fun onLikeClicked(value: Property) {
        //TODO ws call
    }

    override fun onShareClicked(value: Property) {
        navigate(Navigation.ShareNavigation(value))
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ExtraKeys.FilterActivity.SEARCH_REQ_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.hasExtra(ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY) == true) {
                searchRequest =
                    data.getParcelableExtra(ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY)!!

                searchRequest.let { DebugLog.i(TAG, it.toString()) }
            }
        }
    }

}