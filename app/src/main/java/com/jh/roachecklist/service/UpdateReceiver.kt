package com.jh.roachecklist.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.jh.roachecklist.Const
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.utils.DefaultNotification
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class UpdateReceiver : BroadcastReceiver() {

    @Inject lateinit var pref: AppPreference

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action.equals(Intent.ACTION_MY_PACKAGE_REPLACED)) {

            Toast.makeText( context, "app update!", Toast.LENGTH_SHORT).show()

            pref.getPref()
            pref.isFirst = true

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val alarmIntent = Intent( context, AlarmReceiver::class.java )
            val alarmPendingIntent = PendingIntent.getBroadcast(
                context, DefaultNotification.NOTIFICATION_CODE_DEFAULT, alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val alarmCalendar = Calendar.getInstance()
            alarmCalendar.set(Calendar.HOUR_OF_DAY, pref.hour )
            alarmCalendar.set(Calendar.MINUTE, pref.minute )
            alarmCalendar.set(Calendar.SECOND, 0)
            alarmCalendar.set(Calendar.MILLISECOND, 0)

            val alarmTriggerTime = if ( alarmCalendar.timeInMillis > System.currentTimeMillis() )
                alarmCalendar.timeInMillis
            else
                alarmCalendar.timeInMillis + Const.INTERVAL

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmTriggerTime, AlarmManager.INTERVAL_DAY, alarmPendingIntent)

            val refreshIntent = Intent( context, RefreshReceiver::class.java )
            val refreshPendingIntent = PendingIntent.getBroadcast(
                context, DefaultNotification.NOTIFICATION_CODE_DEFAULT, refreshIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val refreshCalendar = Calendar.getInstance()
            refreshCalendar.set(Calendar.HOUR_OF_DAY, 6)
            refreshCalendar.set(Calendar.MINUTE, 0)
            refreshCalendar.set(Calendar.SECOND, 0)
            refreshCalendar.set(Calendar.MILLISECOND, 0)

            val refreshTriggerTime = if ( refreshCalendar.timeInMillis > System.currentTimeMillis() )
                refreshCalendar.timeInMillis
            else
                refreshCalendar.timeInMillis + Const.INTERVAL

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, refreshTriggerTime, AlarmManager.INTERVAL_DAY, refreshPendingIntent)


        }

    }

}