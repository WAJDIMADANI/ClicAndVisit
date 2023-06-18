package com.clickandvisit.global.listener

import com.clickandvisit.data.model.property.Property

interface OnPropertyClickedListener {
    fun onItemClicked(value: Property)
}