package com.clickandvisit.global.listener

import com.clickandvisit.global.helper.PaginationState

interface PaginationStateListener {
    fun setState(newPaginationState: PaginationState)
}
