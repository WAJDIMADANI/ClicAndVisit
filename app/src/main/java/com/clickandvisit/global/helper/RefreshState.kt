package com.clickandvisit.global.helper

sealed class RefreshState {
    object Refreshing : RefreshState()
    object RefreshingDone : RefreshState()
    data class RefreshingError(val throwable: Throwable) : RefreshState()
}