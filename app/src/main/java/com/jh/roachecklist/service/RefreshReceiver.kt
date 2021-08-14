package com.jh.roachecklist.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
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
class RefreshReceiver : BroadcastReceiver() {

    @Inject lateinit var checkListUtil: CheckListUtil

    override fun onReceive(context: Context, intent: Intent) {

        checkListUtil.resetCheckList()

    }

}