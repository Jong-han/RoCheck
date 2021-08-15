package com.jh.roachecklist.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.jh.roachecklist.Const
import com.jh.roachecklist.Const.Rest.CHAOS_DUNGEON_COUNT
import com.jh.roachecklist.Const.Rest.CHARGE_REST_BONUS
import com.jh.roachecklist.Const.Rest.CONSUME_REST_BONUS
import com.jh.roachecklist.Const.Rest.DAILY_EFONA_COUNT
import com.jh.roachecklist.Const.Rest.DAILY_GUARDIAN_COUNT
import com.jh.roachecklist.Const.Rest.MAX_REST_BONUS
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.repository.Repository
import com.jh.roachecklist.utils.CheckListUtil
import com.jh.roachecklist.utils.DefaultNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class RebootReceiver : BroadcastReceiver() {

    @Inject lateinit var pref: AppPreference

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action.equals("android.intent.action.BOOT_COMPLETED")) {

            pref.getPref()

            Log.i("asdf","기기가 부팅됨")

            Toast.makeText(context, "hour: ${pref.hour} minute: ${pref.minute}", Toast.LENGTH_LONG).show();

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
                alarmCalendar.timeInMillis + Const.TEST_INTERVAL

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmTriggerTime, AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmPendingIntent)

            val refreshIntent = Intent( context, RefreshReceiver::class.java )
            val refreshPendingIntent = PendingIntent.getBroadcast(
                context, DefaultNotification.NOTIFICATION_CODE_DEFAULT, refreshIntent,
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