package com.clickandvisit.global.service


import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.Push
import com.clickandvisit.global.utils.TAG
import com.clickandvisit.global.utils.showNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

class MyFirebaseService : FirebaseMessagingService() {


    @Inject
    lateinit var userRepository: UserRepository


    override fun onCreate() {
    }

    override fun onNewToken(token: String) {
        DebugLog.d(TAG, "onNewToken: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage.data.containsKey(Push.NOTIFICATION_KEY_VISIT)) {
            userRepository.setVisits(true)
        } else if (remoteMessage.data.containsKey(Push.NOTIFICATION_KEY_MEET)) {
            userRepository.setMeet(true)
        } else {
            userRepository.setChat(true)
        }


        val key = if (remoteMessage.data.containsKey(Push.NOTIFICATION_KEY_VISIT)) {
            Push.NOTIFICATION_KEY_VISIT
        } else if (remoteMessage.data.containsKey(Push.NOTIFICATION_KEY_MEET)) {
            Push.NOTIFICATION_KEY_MEET
        } else {
            Push.NOTIFICATION_KEY_CHAT
        }

        if (remoteMessage.notification != null) {
            sendContent(
                remoteMessage.notification!!.title,
                remoteMessage.notification!!.body!!,
                key
            )
        }
    }

    private fun sendContent(title: String?, body: String, key: String) {
        showNotification(applicationContext, title, body, key)
    }

}