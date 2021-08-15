package com.jh.roachecklist.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jh.roachecklist.utils.CheckListUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject lateinit var checkListUtil: CheckListUtil

    override fun onReceive(context: Context, intent: Intent) {

        checkListUtil.validAlarm()

    }

}