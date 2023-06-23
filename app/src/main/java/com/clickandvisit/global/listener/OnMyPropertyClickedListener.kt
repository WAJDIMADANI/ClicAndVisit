package com.clickandvisit.global.listener

import com.clickandvisit.data.model.property.Property

interface OnMyPropertyClickedListener {
    fun onItemClicked(value: Property)
    fun onShareClicked(value: Property)
    fun onEditClicked(value: Property)
    fun onRateClicked(value: Property)
    fun onMeetClicked(value: Property)
}