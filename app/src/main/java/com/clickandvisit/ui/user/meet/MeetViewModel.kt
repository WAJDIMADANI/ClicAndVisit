package com.clickandvisit.ui.user.meet

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.clickandvisit.R
import com.clickandvisit.base.BaseAndroidViewModel
import com.clickandvisit.data.model.chat.ContactOwnerResponse
import com.clickandvisit.data.model.property.PropertyDetailsResponse
import com.clickandvisit.data.model.property.SearchResponse
import com.clickandvisit.data.model.reservation.Reservation
import com.clickandvisit.data.model.reservation.ReservationAcceptResponse
import com.clickandvisit.data.model.reservation.ReservationResponse
import com.clickandvisit.data.model.user.ReportUserResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.listener.OnMeetClickedListener
import com.clickandvisit.global.listener.OnSendClickedListener
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
class MeetViewModel
@Inject constructor(
    application: Application,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application, schedulerProvider), ToolBarListener, OnMeetClickedListener,
    OnSendClickedListener {

    val list: MutableLiveData<List<Reservation>> = MutableLiveData(arrayListOf())

    init {
    }

    fun getReservations(accept: Boolean) {
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.getReservations(accept)
                }
                onGetReservationsSuccess(response)
            }, { error ->
                onGetReservationsError(error)
            })
        }
    }

    private fun onGetReservationsSuccess(response: ReservationResponse) {
        hideBlockProgressBar()
        list.value = response.reservations
    }

    private fun onGetReservationsError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }


    fun onBackClick() {
        navigate(Navigation.Back)
    }

    override fun onItemClicked(value: Reservation) {
        navigate(Navigation.VisitsNavigation(value.reservationDetails!!.propertyId))
    }

    override fun onConfirmClicked(value: Reservation) {
        acceptRefuseReservation(value, true)
    }

    override fun onRejectClicked(value: Reservation) {
        acceptRefuseReservation(value, false)
    }

    override fun onCancelClicked(value: Reservation) {
        acceptRefuseReservation(value, false)
    }

    override fun onChatClicked(reservation: Reservation) {
        showMeetBottomSheet(
            title = applicationContext.getString(R.string.chat_title),
            hint = applicationContext.getString(R.string.chat_body),
            reservation = reservation,
            onSendClickedListener = this
        )
    }

    override fun onSignalClicked(reservation: Reservation) {
        showMeetBottomSheet(
            title = applicationContext.getString(R.string.report_title),
            hint = applicationContext.getString(R.string.report_body),
            reservation = reservation,
            onSendClickedListener = this
        )
    }

    override fun onPhoneClick(value: Reservation) {
        navigate(Navigation.Phone(value.owner.phone))
    }

    private fun acceptRefuseReservation(value: Reservation, accept: Boolean) {
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.acceptRefuseReservation(
                        value.id,
                        value.reservationDetails!!.id,
                        accept
                    )
                }
                onAcceptRefuseReservationSuccess(response)
            }, { error ->
                onGetReservationsError(error)
            })
        }
    }

    private fun onAcceptRefuseReservationSuccess(response: ReservationAcceptResponse) {
        hideBlockProgressBar()
        navigate(Navigation.Back)
    }


    fun getPropertyDetails(propertyId: Int) {
        showBlockProgressBar()
        viewModelScope.launch {
            tryCatch({
                val response = withContext(schedulerProvider.dispatchersIO()) {
                    userRepository.propertyDetails(propertyId)
                }
                onGetPropertySuccess(response)
            }, { error ->
                onGetPropertyError(error)
            })
        }
    }

    private fun onGetPropertySuccess(response: PropertyDetailsResponse) {
        hideBlockProgressBar()
        navigate(Navigation.AdsDetailsActivityNavigation(response.property))
    }

    private fun onGetPropertyError(throwable: Throwable) {
        hideBlockProgressBar()
        handleThrowable(throwable)
    }

    override fun onSendClicked(reservation: Reservation, tag: String, msg: String) {
        if (tag == applicationContext.getString(R.string.chat_title)) {
            showBlockProgressBar()
            viewModelScope.launch {
                tryCatch({
                    val response = withContext(schedulerProvider.dispatchersIO()) {
                        userRepository.contactOwner(reservation.owner.id.toInt(), msg)
                    }
                    onContactOwnerSuccess(response)
                }, { error ->
                    onContactOwnerError(error)
                })
            }
        } else {
            showBlockProgressBar()
            viewModelScope.launch {
                tryCatch({
                    val response = withContext(schedulerProvider.dispatchersIO()) {
                        userRepository.reportUser(reservation.owner.id.toInt(), msg)
                    }
                    onReportUserSuccess(response)
                }, { error ->
                    onReportUserError(error)
                })
            }
        }
    }

    private fun onContactOwnerSuccess(response: Void) {
        hideBlockProgressBar()
        shownSimpleDialog(messageId = R.string.chat_response)
    }

    private fun onContactOwnerError(throwable: Throwable) {
        hideBlockProgressBar()
        shownSimpleDialog(messageId = R.string.chat_response)
    }


    private fun onReportUserSuccess(response: Void) {
        hideBlockProgressBar()
        shownSimpleDialog(messageId = R.string.report_response)
    }

    private fun onReportUserError(throwable: Throwable) {
        hideBlockProgressBar()
        shownSimpleDialog(messageId = R.string.report_response)
    }

    fun setVisits(isNotification: Boolean) {
        userRepository.setVisits(isNotification)
    }

    fun setMeet(isNotification: Boolean) {
        userRepository.setMeet(isNotification)
    }

}