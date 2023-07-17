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
        if (remoteMessage.notification != null) {
            sendContent(
                remoteMessage.notification!!.title,
                remoteMessage.notification!!.body!!,
                remoteMessage.data
            )
        }
    }

    private fun sendContent(title: String?, body: String, data: MutableMap<String, String>) {

        data[Push.NOTIFICATION_KEY_CHAT]

        if (data.containsKey(Push.NOTIFICATION_KEY_VISIT)) {
            userRepository.setVisits(true)
        } else if (data.containsKey(Push.NOTIFICATION_KEY_MEET)) {
            userRepository.setMeet(true)
        } else {
            userRepository.setChat(true)
        }


        showNotification(applicationContext, title, body, data)
    }

}