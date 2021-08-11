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
import com.jh.roachecklist.utils.DefaultNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject lateinit var pref: AppPreference
    @Inject lateinit var repository: Repository

    override fun onReceive(context: Context, intent: Intent) {
        val calendar = Calendar.getInstance()

        CoroutineScope( Dispatchers.IO).launch {

            val nickNameList = repository.getNickNameList()

            withContext( Dispatchers.Main ) {

                Log.i("asdf","닉네임 리스트 :: ${nickNameList.size}")
                Log.i("asdf","오늘 요일 :: ${calendar.get(Calendar.DAY_OF_WEEK)}")

                for ( nickName in nickNameList ) {

                    pref.getPref( nickName )
                    val dailyList = pref.getDailyList()

                    Log.i("asdf","start calculate ::$nickName")

                    for ( index in 0 until dailyList.size ) {

                        when ( index ) {

                            Const.DailyIndex.DAILY_EFONA -> {

                                pref.consumeEfonaRestBonus( dailyList[index] )
                                pref.efonaRestBonus = if ( pref.efonaRestBonus  + ( ( DAILY_EFONA_COUNT - dailyList[index] ) * CHARGE_REST_BONUS )  <= MAX_REST_BONUS ) {

                                    pref.efonaRestBonus  + ( ( DAILY_EFONA_COUNT - dailyList[index] ) * CHARGE_REST_BONUS )

                                }
                                else {

                                    MAX_REST_BONUS

                                }

                            }
                            Const.DailyIndex.DAILY_GUARDIAN -> {

                                pref.consumeGuardianRestBonus( dailyList[index] )
                                pref.guardianRestBonus = if ( pref.guardianRestBonus  + ( ( DAILY_GUARDIAN_COUNT - dailyList[index] ) * CHARGE_REST_BONUS )  <= MAX_REST_BONUS ) {

                                    pref.guardianRestBonus  + ( ( DAILY_GUARDIAN_COUNT - dailyList[index] ) * CHARGE_REST_BONUS )

                                }
                                else {

                                    MAX_REST_BONUS

                                }

                            }
                            Const.DailyIndex.CHAOS_DUNGEON -> {

                                pref.consumeChaosRestBonus( dailyList[index] )
                                pref.chaosRestBonus = if ( pref.chaosRestBonus  + ( ( CHAOS_DUNGEON_COUNT - dailyList[index] ) * CHARGE_REST_BONUS )  <= MAX_REST_BONUS ) {

                                    pref.chaosRestBonus  + ( ( CHAOS_DUNGEON_COUNT - dailyList[index] ) * CHARGE_REST_BONUS )

                                }
                                else {

                                    MAX_REST_BONUS

                                }

                            }

                        }

                    }
                    pref.resetDaily()
                    if ( calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY ) {

                        pref.resetWeekly()
                        pref.resetExpedition()

                    }

                }


                DefaultNotification.startNotification( context, "로첵", "타입" )

            }

        }

    }


}