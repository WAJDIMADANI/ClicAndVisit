package com.clickandvisit.global.helper

sealed class PaginationState {

    object Loading : PaginationState()
    object LoadingDone : PaginationState()
    data class LoadingError(val throwable: Throwable) : PaginationState()

    object LoadingInitial : PaginationState()
    data class LoadingInitialDone(val isEmpty: Boolean) : PaginationState()
    data class LoadingInitialError(val throwable: Throwable) : PaginationState()
}