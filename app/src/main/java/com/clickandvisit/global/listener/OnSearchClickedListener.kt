package com.clickandvisit.global.listener

import com.clickandvisit.data.model.property.SearchData

interface OnSearchClickedListener {
    fun onNotifyClick(data: SearchData)
    fun onDeleteClick(data: SearchData)
    fun onSeeClick(data: SearchData)
}