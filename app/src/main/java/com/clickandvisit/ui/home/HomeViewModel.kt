package com.clickandvisit.ui.home

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.GlobalResponse
import com.clickandvisit.data.model.property.*
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
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener,
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
        navigate(Navigation.AdsDetailsActivityNavigation(value))
    }

    override fun onLikeClicked(value: Property) {
        showBlockProgressBar()
        val action = if (value.isFavorite) {
            REMOVE
        } else {
            ADD
        }
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.addRemoveFavorite(FavoriteRequest(value.id, action))
                }
                onLikeClickedSuccess(response)
            }, { error ->
                onLikeClickedError(error)
            })
        }
    }

    private fun onLikeClickedSuccess(response: GlobalResponse) {
        hideBlockProgressBar()
        getSearch()
    }

    private fun onLikeClickedError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }

    override fun onShareClicked(value: Property) {
        navigate(Navigation.ShareNavigation(value))
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ExtraKeys.FilterActivity.SEARCH_REQ_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.hasExtra(ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY) == true) {
                searchRequest =
                    data.getParcelableExtra(ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY)!!

                showBlockProgressBar()
                viewModelScope.launch {
                    tryCatch({
                        val response = withContext(schedulerProvider.dispatchersIO()) {
                            userRepository.search(searchRequest)
                        }
                        onGetDiscussionSuccess(response)
                    }, { error ->
                        onGetDiscussionError(error)
                    })
                }

                searchRequest.let { DebugLog.i(TAG, it.toString()) }
            }
        }
    }

}