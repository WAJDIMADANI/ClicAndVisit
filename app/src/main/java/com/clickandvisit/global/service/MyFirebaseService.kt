package com.clickandvisit.global.service


import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.clickandvisit.global.helper.BackgroundManager
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.Push
import com.clickandvisit.global.utils.TAG
import com.clickandvisit.global.utils.showNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseService : FirebaseMessagingService() {

    override fun onCreate() {
    }

    override fun onNewToken(token: String) {
        DebugLog.d(TAG, "onNewToken: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            sendContent(
                remoteMessage.notification!!.title,
                remoteMessage.notification!!.body!!,
                remoteMessage.data
            )
        }
    }

    private fun sendContent(title: String?, body: String, data: MutableMap<String, String>) {

        if (!BackgroundManager.getInstance().isInBackground) {

            val intent = Intent(Push.NOTIFICATION)
            intent.putExtra(Push.NOTIFICATION_TITLE, title)
            intent.putExtra(Push.NOTIFICATION_BODY, body)
            //intent.putExtra(Push.NOTIFICATION_DATA, data)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)

        } else {
            showNotification(applicationContext, title, body, data)
        }

    }

}