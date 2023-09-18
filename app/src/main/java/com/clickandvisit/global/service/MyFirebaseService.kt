package com.clickandvisit.global.service


import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.Push
import com.clickandvisit.global.utils.TAG
import com.clickandvisit.global.utils.showNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseService : FirebaseMessagingService() {


    @Inject
    lateinit var userRepository: UserRepository


    override fun onCreate() {
        super.onCreate()
    }

    override fun onNewToken(token: String) {
        DebugLog.d(TAG, "onNewToken: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        DebugLog.i("NOTIFICATION_KEY/msg", "NOTIFICATION_KEY")
        DebugLog.i("NOTIFICATION_KEY/val", remoteMessage.data[Push.NOTIFICATION_KEY].toString())

            val value = remoteMessage.data[Push.NOTIFICATION_KEY]
            DebugLog.i("NOTIFICATION_KEY/value", value.toString())
            if (remoteMessage.notification != null) {
                sendContent(
                    remoteMessage.notification!!.title,
                    remoteMessage.notification!!.body!!,
                    value!!
                )
            }

        when (value) {
            Push.NOTIFICATION_VAL_VISIT -> {
                DebugLog.i("NOTIFICATION_KEY/when", "setVisits(true)")
                userRepository.setVisits(true)
            }

            Push.NOTIFICATION_VAL_MEET -> {
                DebugLog.i("NOTIFICATION_KEY/when", "setMeet(true)")
                userRepository.setMeet(true)
            }

            Push.NOTIFICATION_VAL_CHAT -> {
                DebugLog.i("NOTIFICATION_KEY/when", "setChat(true)")
                userRepository.setChat(true)
            }
        }

    }

    private fun sendContent(title: String?, body: String, value: String) {
        showNotification(applicationContext, title, body, value)
    }

}