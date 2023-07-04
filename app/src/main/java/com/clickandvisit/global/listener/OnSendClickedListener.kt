package com.clickandvisit.global.listener

import com.clickandvisit.data.model.reservation.Reservation

interface OnSendClickedListener {
    fun onSendClicked(reservation: Reservation, tag: String, msg: String)
}