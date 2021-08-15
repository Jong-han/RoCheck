package com.jh.roachecklist

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.repository.Repository
import com.jh.roachecklist.service.AlarmReceiver
import com.jh.roachecklist.service.RefreshReceiver
import com.jh.roachecklist.utils.DefaultNotification
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    @Inject
    lateinit var pref: AppPreference
    @Inject
    lateinit var repository: Repository

    override fun onCreate() {

        super.onCreate()
        pref.getPref()
        if ( pref.isFirst ) {

            CoroutineScope( Dispatchers.IO).launch {

                repository.insertCheckList()
                pref.isFirst = false

            }
            val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val alarmIntent = Intent( applicationContext, AlarmReceiver::class.java )
            val alarmPendingIntent = PendingIntent.getBroadcast(
                applicationContext, DefaultNotification.NOTIFICATION_CODE_DEFAULT, alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val alarmCalendar = Calendar.getInstance()
            alarmCalendar.set(Calendar.HOUR_OF_DAY, pref.hour )
            alarmCalendar.set(Calendar.MINUTE, pref.minute )
            alarmCalendar.set(Calendar.SECOND, 0)
            alarmCalendar.set(Calendar.MILLISECOND, 0)

            val alarmTriggerTime = if ( alarmCalendar.timeInMillis > System.currentTimeMillis() )
                alarmCalendar.timeInMillis
            else
                alarmCalendar.timeInMillis + Const.TEST_INTERVAL

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmTriggerTime, AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmPendingIntent)

            val refreshIntent = Intent( applicationContext, RefreshReceiver::class.java )
            val refreshPendingIntent = PendingIntent.getBroadcast(
                applicationContext, DefaultNotification.NOTIFICATION_CODE_DEFAULT, refreshIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val refreshCalendar = Calendar.getInstance()
            refreshCalendar.set(Calendar.HOUR_OF_DAY, 13)
            refreshCalendar.set(Calendar.MINUTE, 25)
            refreshCalendar.set(Calendar.SECOND, 0)
            refreshCalendar.set(Calendar.MILLISECOND, 0)

            val refreshTriggerTime = if ( refreshCalendar.timeInMillis > System.currentTimeMillis() )
                refreshCalendar.timeInMillis
            else
                refreshCalendar.timeInMillis + Const.TEST_INTERVAL

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, refreshTriggerTime, AlarmManager.INTERVAL_FIFTEEN_MINUTES, refreshPendingIntent)


        }

    }

}