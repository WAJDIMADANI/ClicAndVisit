package com.clickandvisit.global.listener

import com.clickandvisit.data.model.property.Property

interface OnPropertyClickedListener {
    fun onVisitNowClicked(value: Property)
    fun onGoScrollClicked(value: Property)
    fun onItemClicked(value: Property)
    fun onLikeClicked(value: Property)
    fun onShareClicked(value: Property)
}