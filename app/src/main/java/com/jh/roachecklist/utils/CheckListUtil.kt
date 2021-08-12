package com.jh.roachecklist.utils

import android.content.Context
import android.util.Log
import com.jh.roachecklist.Const
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CheckListUtil( private val context: Context, private val pref: AppPreference, private val repository: Repository ) {

    fun resetCheckList() {

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
                                pref.efonaRestBonus = if ( pref.efonaRestBonus  + ( ( Const.Rest.DAILY_EFONA_COUNT - dailyList[index] ) * Const.Rest.CHARGE_REST_BONUS )  <= Const.Rest.MAX_REST_BONUS ) {

                                    pref.efonaRestBonus  + ( ( Const.Rest.DAILY_EFONA_COUNT - dailyList[index] ) * Const.Rest.CHARGE_REST_BONUS )

                                }
                                else {

                                    Const.Rest.MAX_REST_BONUS

                                }

                            }
                            Const.DailyIndex.DAILY_GUARDIAN -> {

                                pref.consumeGuardianRestBonus( dailyList[index] )
                                pref.guardianRestBonus = if ( pref.guardianRestBonus  + ( ( Const.Rest.DAILY_GUARDIAN_COUNT - dailyList[index] ) * Const.Rest.CHARGE_REST_BONUS )  <= Const.Rest.MAX_REST_BONUS ) {

                                    pref.guardianRestBonus  + ( ( Const.Rest.DAILY_GUARDIAN_COUNT - dailyList[index] ) * Const.Rest.CHARGE_REST_BONUS )

                                }
                                else {

                                    Const.Rest.MAX_REST_BONUS

                                }

                            }
                            Const.DailyIndex.CHAOS_DUNGEON -> {

                                pref.consumeChaosRestBonus( dailyList[index] )
                                pref.chaosRestBonus = if ( pref.chaosRestBonus  + ( ( Const.Rest.CHAOS_DUNGEON_COUNT - dailyList[index] ) * Const.Rest.CHARGE_REST_BONUS )  <= Const.Rest.MAX_REST_BONUS ) {

                                    pref.chaosRestBonus  + ( ( Const.Rest.CHAOS_DUNGEON_COUNT - dailyList[index] ) * Const.Rest.CHARGE_REST_BONUS )

                                }
                                else {

                                    Const.Rest.MAX_REST_BONUS

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

