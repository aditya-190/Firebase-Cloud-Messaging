package com.bhardwaj.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bhardwaj.messaging.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val CHANNEL_ID_1 = "1"
    private val CHANNEL_ID_2 = "2"
    private lateinit var mContext: Context

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mContext = this@MainActivity

        myFirebaseRegistration()
        notificationChannel()

        binding.btn.setOnClickListener {
            val numberOfCookies = binding.editCookies.text.toString()

            subscribeToTopic(numberOfCookies.toInt())

            val intent = Intent(mContext, MainActivity2::class.java)
            intent.putExtra("cookies", numberOfCookies)
            val pendingIntent = PendingIntent.getActivity(
                mContext,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val largeIconBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_cookies)

//            val notificationLayout = RemoteViews(packageName, R.layout.notification_small)
//            val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_large)
//            notificationLayoutExpanded.setImageViewResource(R.id.ivCookies, R.drawable.icon_cookies)
//            notificationLayoutExpanded.setTextViewText(
//                R.id.tvBoughtCookies,
//                "You have Bought $numberOfCookies cookies!"
//            )

            val builder = NotificationCompat.Builder(mContext, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.icon_message)
//                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
//                .setCustomContentView(notificationLayout)
//                .setCustomBigContentView(notificationLayoutExpanded)
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
                .addAction(R.mipmap.ic_launcher, "Option 1", pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Option 2", pendingIntent)
                .setColor(resources.getColor(R.color.purple_700, theme))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            val notificationManagerCompat = NotificationManagerCompat.from(mContext)
            notificationManagerCompat.notify(1, builder)
        }
    }

    private fun notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name1 = "My Channel Name - 1"
            val name2 = "My Channel Name - 2"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel1 = NotificationChannel(CHANNEL_ID_1, name1, importance)
            channel1.description = "My Channel Description - 1"

            val channel2 = NotificationChannel(CHANNEL_ID_2, name2, importance)
            channel2.description = "My Channel Description - 2"

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)
        }
    }

    private fun myFirebaseRegistration() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            Log.d("Aditya", "myFirebaseRegistration: $token")
        })
    }

    private fun subscribeToTopic(numberOfCookiesEvenOrOdd: Int) {
        if (numberOfCookiesEvenOrOdd % 2 == 0) {
            FirebaseMessaging.getInstance().subscribeToTopic("Even")
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    } else {
                        Toast.makeText(mContext, "Subscribed to Topic - Even", Toast.LENGTH_SHORT)
                            .show()
                    }
                })

            FirebaseMessaging.getInstance().unsubscribeFromTopic("Odd")
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    } else {
                        Toast.makeText(mContext, "UnSubscribed to Topic - Odd", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        } else {
            FirebaseMessaging.getInstance().subscribeToTopic("Odd")
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    } else {
                        Toast.makeText(mContext, "Subscribed to Topic - Odd", Toast.LENGTH_SHORT)
                            .show()
                    }
                })

            FirebaseMessaging.getInstance().unsubscribeFromTopic("Even")
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    } else {
                        Toast.makeText(mContext, "Unsubscribed to Topic - Even", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        }
    }
}