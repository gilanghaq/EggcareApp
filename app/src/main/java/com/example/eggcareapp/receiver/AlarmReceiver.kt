package com.example.eggcareapp.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.eggcareapp.R
import com.example.eggcareapp.service.AlarmService
import com.example.eggcareapp.util.Constants
import java.util.Calendar
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME, 0L)
        when (intent.action) {
            Constants.ACTION_SET_REPETITIVE_ALARM -> {
                val cal = Calendar.getInstance().apply {
                    this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(1)
                }
                AlarmService(context).setRepetitiveAlarm(cal.timeInMillis)
                buildNotification(context)
            }
        }
    }

    private fun buildNotification(context: Context) {
        val channelId = "alarm_notification_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Membuat channel notifikasi untuk Android Oreo ke atas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Alarm Notification"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelId, channelName, importance)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.description = "Alarm Notification Channel"
            notificationManager.createNotificationChannel(channel)
        }

        // Membangun notifikasi
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("It's Eggcare Time!")
            .setContentText("Take care of your chickens and produce high-quality eggs🥚")
            .setColor(ContextCompat.getColor(context, R.color.primary))
            .setAutoCancel(true)

        // Menampilkan notifikasi
        notificationManager.notify(0, builder.build())
    }
}
