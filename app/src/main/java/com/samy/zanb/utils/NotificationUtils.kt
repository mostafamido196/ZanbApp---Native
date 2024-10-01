package com.samy.zanb.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.samy.zanb.R
import com.samy.zanb.data.BookServices
import com.samy.zanb.ui.splashscreen.SplashScreenActivity
import com.samy.zanb.utils.Utils.myLog

object NotificationUtils {
    private const val CHANNEL_ID = "daily_notification_channel"
    private const val CHANNEL_NAME = "Advice"
    private const val NOTIFICATION_ID = 500
    const val NOTIFICATION_PERMISSION_REQUEST_CODE = 111

    const val EXTRA_NOTIFICATION = "com.example.EXTRA_NOTIFICATION"

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateNotification(context: Context, s: String) {
        createNotification(context)
        val builder = handleNotification(context, s)
        showNotification(context, builder)
    }


    private fun createNotification(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = "descriptionText"
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                context.getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleNotification(context: Context, s: String): NotificationCompat.Builder {
        val page = getTitlePage(context)
        // Create an explicit intent for an Activity in your app.
        val intent = Intent(context, SplashScreenActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(EXTRA_NOTIFICATION, page.first)
            myLog("NotificationUtils:handleNotification:Intent:page.first: ${page.first} ")
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(
                context,
                NOTIFICATION_PERMISSION_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.tree)
            .setContentTitle(s)
            .setContentText(page.second)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        setLargeIcon(builder,context)

        return builder
    }

    private fun setLargeIcon(builder: NotificationCompat.Builder, context: Context) {
        val futureTarget = Glide.with(context)
            .asBitmap()
            .load(R.drawable.img_notify)
            .submit()

        val bitmap = futureTarget.get()
        builder.setLargeIcon(bitmap)

        Glide.with(context).clear(futureTarget)

    }

    private fun showNotification(context: Context, builder: NotificationCompat.Builder) {
        myLog("showNotification")
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                myLog("if")
                // ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                // public fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                //                                        grantResults: IntArray)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                return@with
            }
            // notificationId is a unique int for each notification that you must define.
            notify(NOTIFICATION_ID, builder.build())
        }


    }



    private fun getTitlePage(context: Context): Pair<Int, String> {

        var num =
            Utils.getSharedPreferencesInt(context, Constants.GANNA, Constants.LASTINDEXREAD, 0)
        Utils.setSharedPreferencesInt(context, Constants.GANNA, Constants.LASTINDEXREAD, num + 1)
        if (num >= 59) {
            Utils.setSharedPreferencesInt(context, Constants.GANNA, Constants.LASTINDEXREAD, 1)
            num = 1
        }
        var title = BookServices().getBook().arr[num + 1].title
        if (title[title.length - 1] == '.')
            title = title.substring(0, title.length - 1)
        if (title[title.length - 1] == ' ')
            title = title.substring(0, title.length - 1)

        if (title[2] == '-') {
            title = title.substring(3, title.length)
        } else {
            title = title.substring(2, title.length)
        }
        //if title.lenth > 50 ---> 47+...
        if (title.length > 45) {
            title = title.substring(0, 42) + "..."
        }
        return Pair(num + 1, title)
    }

}
