package fr.tahia910.android.positivityboost.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import fr.tahia910.android.positivityboost.R
import fr.tahia910.android.positivityboost.ui.MainActivity
import fr.tahia910.android.positivityboost.utils.NOTIFICATION_ID

// From the FCM codelab: https://github.com/googlecodelabs/android-kotlin-notifications-fcm/

fun NotificationManager.sendNotification(messageBody: String?, applicationContext: Context) {

    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val sunImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.ic_sun_primary
    )

    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(sunImage)
        .bigLargeIcon(null)

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.quote_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_sun_primary)
        .setContentTitle(applicationContext.getString(R.string.quote_notification_channel_name))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setStyle(bigPicStyle)
        .setLargeIcon(sunImage)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}