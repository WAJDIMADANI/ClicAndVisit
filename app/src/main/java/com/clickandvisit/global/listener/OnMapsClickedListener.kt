package com.clickandvisit.global.listener

import com.clickandvisit.data.model.property.Property

interface OnMapsClickedListener {
    fun onItemClicked(property: Property)
    fun onMeetClicked(property: Property)
}