package com.bhardwaj.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    val CHANNEL_ID_3 = "3"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            showNotification(
                remoteMessage.notification!!.title.toString(),
                remoteMessage.notification!!.body.toString()
            )
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showNotification(
        title: String,
        description: String,
    ) {
        createNotificationChannel()

        val intent = Intent(this, MainActivity2::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_3)
            .setSmallIcon(R.drawable.icon_message)
            .setContentTitle(title)
            .setContentText(description)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(3, builder)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name1 = "My Channel Name - 3"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel3 = NotificationChannel(CHANNEL_ID_3, name1, importance)
            channel3.description = "My Channel Description - 3"

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel3)
        }
    }
}