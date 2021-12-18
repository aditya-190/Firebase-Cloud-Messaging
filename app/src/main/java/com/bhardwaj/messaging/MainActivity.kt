package com.bhardwaj.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bhardwaj.messaging.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val CHANNEL_ID = "1"
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mContext = this@MainActivity

        notificationChannel()

        binding.btn.setOnClickListener {
            val intent = Intent(mContext, MainActivity2::class.java)
            val pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0)

            val largeIconBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_cookies)

            val numberOfCookies = binding.editCookies.text.toString()
            val builder = NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_message)
                .setContentTitle("Cookies")
                .setContentText("You got $numberOfCookies Cookies")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setLargeIcon(largeIconBitmap)
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(largeIconBitmap)
                        .bigLargeIcon(null)
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            val notificationManagerCompat = NotificationManagerCompat.from(mContext)
            notificationManagerCompat.notify(1, builder)
        }
    }

    private fun notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Channel Name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = "My Channel Description"

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}