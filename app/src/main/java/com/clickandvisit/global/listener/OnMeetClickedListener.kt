package com.clickandvisit.global.listener

import com.clickandvisit.data.model.reservation.Reservation


interface OnMeetClickedListener {
    fun onItemClicked(value: Reservation)
    fun onConfirmClicked(value: Reservation)
    fun onRejectClicked(value: Reservation)
    fun onCancelClicked(value: Reservation)

    fun onChatClicked(value: Reservation)
    fun onSignalClicked(value: Reservation)
    fun onPhoneClick(value: Reservation)
}