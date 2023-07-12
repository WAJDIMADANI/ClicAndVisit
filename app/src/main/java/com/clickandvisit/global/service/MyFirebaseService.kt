package com.clickandvisit.global.service


import com.clickandvisit.global.utils.DebugLog
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
                remoteMessage.data.isNotEmpty()
            )
        }
    }

    private fun sendContent(title: String?, body: String, isDataExist: Boolean) {
        showNotification(applicationContext, title, body, isDataExist)
/*
        if (!BackgroundManager.getInstance().isInBackground) {

            val intent = Intent(Push.NOTIFICATION)
            intent.putExtra(Push.NOTIFICATION_TITLE, title)
            intent.putExtra(Push.NOTIFICATION_BODY, body)
            intent.putExtra(Push.NOTIFICATION_DATA, isDataExist)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)

        } else {

        }
*/
    }

}