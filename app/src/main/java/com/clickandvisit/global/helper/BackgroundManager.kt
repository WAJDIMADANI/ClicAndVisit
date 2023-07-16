package com.clickandvisit.global.helper

/**
 * Created by sazouzi on 15/07/2023
 */
class BackgroundManager private constructor() {
    private var state = 0
    fun onStart() {
        state++
    }

    fun onStop() {
        state--
    }

    val isInBackground: Boolean
        get() = state == 0

    companion object {
        private var instance: BackgroundManager? = null
        fun getInstance(): BackgroundManager {
            if (instance == null) instance = BackgroundManager()
            return instance!!
        }
    }
}