package com.foodline.global.helper

sealed class FetchState {

    object Fetching : FetchState()
    data class FetchDone(val isEmpty : Boolean) : FetchState()
    data class FetchError(val throwable : Throwable) : FetchState()

}