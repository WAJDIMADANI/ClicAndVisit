package com.clickandvisit.global.listener

import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.TAG

/**
 * Created on 2/2/18.
 */

interface ToolBarListener {

    fun onStartClicked() {
        DebugLog.d(TAG, "onStartClicked but not handled")
    }

    fun onEndClicked() {
        DebugLog.d(TAG, "onStartClicked but not handled")
    }

    fun onBMenuClicked() {
        DebugLog.d(TAG, "onBMenuClicked but not handled")
    }

    fun onSearchClicked() {
        DebugLog.d(TAG, "onSearchClicked but not handled")
    }

    fun onChatClicked() {
        DebugLog.d(TAG, "onChatClicked but not handled")
    }

    fun onProfileClicked() {
        DebugLog.d(TAG, "onProfileClicked but not handled")
    }

}
