package com.foodline.global.listener

import com.foodline.global.helper.PaginationState

interface PaginationStateListener {
    fun setState(newPaginationState: PaginationState)
}
