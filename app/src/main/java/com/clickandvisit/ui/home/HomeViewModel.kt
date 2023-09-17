package com.clickandvisit.ui.home

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.GlobalResponse
import com.clickandvisit.data.model.property.*
import com.clickandvisit.data.model.reservation.ReserveResponse
import com.clickandvisit.data.model.user.TokenResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.OnFilterClickedListener
import com.clickandvisit.global.listener.OnPropertyClickedListener
import com.clickandvisit.global.listener.SchedulerProvider
import com.clickandvisit.global.listener.ToolBarListener
import com.clickandvisit.global.utils.*
import com.clickandvisit.ui.ads.adsdetails.CalendarUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
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
    OnPropertyClickedListener, OnFilterClickedListener {

    val list: MutableLiveData<List<Property>> = MutableLiveData(arrayListOf())

    val listCount: MutableLiveData<String> = MutableLiveData()

    var searchRequest = SearchRequest(sortBy = "date", sortHow = "desc")

    lateinit var searchResponse: SearchResponse

    var notifKey: String?


    init {
        notifKey =
            savedStateHandle.get<String>(ExtraKeys.HomeNotificationKeys.HOME_NOTIFICATION_EXTRA_KEY)

        if (notifKey?.contains(Push.NOTIFICATION_KEY_VISIT) == true) {
            navigate(Navigation.VisitsActivityNav)
        } else if (notifKey?.contains(Push.NOTIFICATION_KEY_MEET) == true) {
            navigate(Navigation.MeetActivityNav)
        } else if (notifKey?.contains(Push.NOTIFICATION_KEY_CHAT) == true) {
            navigate(Navigation.ChatActivityNavigation)
        }


        if (userRepository.isConnected()) {
            setPushToken()
        }
    }

    fun getUserId(): Int {
        return userRepository.getCurrentUserId()
    }

    fun onResume() {
        getSearch(null, null)
    }


    private fun getSearch(sortBy: String?, sortHow: String?) {
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    if (userRepository.isConnected()) {
                        userRepository.getHomeProperty()
                    } else {
                        userRepository.search(sortBy, sortHow)
                    }
                }
                onGetSearchSuccess(response)
            }, { error ->
                onGetSearchError(error)
            })
        }
    }


    private fun onGetSearchSuccess(response: SearchResponse) {
        hideBlockProgressBar()
        searchResponse = response
        list.value = response.properties
        listCount.value =
            "${applicationContext.getString(R.string.home_list)}(${response.properties.count()})"
    }

    private fun onGetSearchError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ExtraKeys.FilterActivity.SEARCH_REQ_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.hasExtra(ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY) == true) {
                searchRequest =
                    data.getParcelableExtra(ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY)!!

                viewModelScope.launch {
                    tryCatch({
                        val response = withContext(schedulerProvider.dispatchersIO()) {
                            userRepository.search(searchRequest)
                        }
                        onGetSearchSuccess(response)
                    }, { error ->
                        onGetSearchError(error)
                    })
                }
            }
        }
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
        showFilterBottomSheet(this)
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onVisitNowClicked(property: Property) {
        val now = Calendar.getInstance()
        now.set(Calendar.MINUTE, 0)
        val sdf = SimpleDateFormat("HH:mm")

        shownChoseDialog(
            title = null,
            message = "Voulez-vous confirmer votre RDV pour le ${
                CalendarUtils.getWsFormattedDate(
                    LocalDate.now()
                )
            } à ${sdf.format(now.time)} ?",
            "Oui",
            "Non",
            okActionBlock = {
                viewModelScope.launch {
                    tryCatch({
                        val response = withContext(schedulerProvider.dispatchersIO()) {
                            userRepository.reserve(
                                property.id,
                                "${
                                    CalendarUtils.getWsFormattedDate(
                                        LocalDate.now()
                                    )
                                } ${sdf.format(now.time)}"
                            )
                        }
                        onReserveSuccess(response)
                    }, { error ->
                        onLikeClickedError(error)
                    })
                }
            }, dismissActionBlock = null
        )
    }


    private fun onReserveSuccess(response: ReserveResponse) {

    }

    override fun onItemClicked(value: Property) {
        if (userRepository.isConnected()) {
            navigate(Navigation.AdsDetailsActivityNavigation(value))
        } else {
            navigate(Navigation.SignInActivityNavigation)
        }
    }

    override fun onLikeClicked(value: Property) {
        if (userRepository.isConnected()) {
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
        } else {
            navigate(Navigation.SignInActivityNavigation)
        }
    }

    private fun onLikeClickedSuccess(response: GlobalResponse) {
        hideBlockProgressBar()
        getSearch(null, null)
    }

    private fun onLikeClickedError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }

    override fun onShareClicked(value: Property) {
        navigate(Navigation.ShareNavigation(value))
    }


    override fun onDateClicked() {
        getSearch("date", "desc")
    }

    override fun onPriceAscClicked() {
        getSearch("price", "asc")
    }

    override fun onPriceDescClicked() {
        getSearch("price", "desc")
    }

    override fun onSurfaceAscClicked() {
        getSearch("surface", "asc")
    }

    override fun onSurfaceDescClicked() {
        getSearch("surface", "desc")
    }

    override fun onCancelClicked() {
        DebugLog.i(TAG, "onCancelClicked")
    }

    fun getChat() = userRepository.getChat()

    fun getVisits() = userRepository.getVisits()

    fun getMeet() = userRepository.getMeet()

}