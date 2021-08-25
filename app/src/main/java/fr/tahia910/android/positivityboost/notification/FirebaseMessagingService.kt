package fr.tahia910.android.positivityboost.notification

import android.app.NotificationManager
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {

    // Get the device notification token
    override fun onNewToken(token: String) {
        super.onNewToken(token)
//        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            sendNotification(it.body)
        }
    }

    private fun sendNotification(messageBody: String?) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        // Use extension from NotificationMaker.kt
        notificationManager.sendNotification(messageBody, applicationContext)
    }
}