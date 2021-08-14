package com.jh.roachecklist.utils

import android.content.Context
import android.util.Log
import com.jh.roachecklist.Const
import com.jh.roachecklist.db.CheckListEntity
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
                    Log.i("asdf","토요일로 되잇는거 꼭 화요일로 바꾸고 출시할것 ${calendar.get(Calendar.DAY_OF_WEEK)}  ${Calendar.SATURDAY}")
                    if ( calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ) {

                        pref.resetWeekly()
                        pref.resetExpedition()

                    }

                }
//                DefaultNotification.( context, "로첵", "타입" )

            }

        }

    }

    fun validAlarm() {

        CoroutineScope( Dispatchers.IO ).launch {

            if ( alarmDaily() )
                DefaultNotification.startDailyNotification( context, "하지 않은 일일 숙제가 있습니다." )

            if ( alarmExpedition() )
                DefaultNotification.startWeeklyNotification( context, "하지 않은 주간 숙제가 있습니다." )
            else {

                if ( alarmWeekly() )
                    DefaultNotification.startWeeklyNotification( context, "하지 않은 주간 숙제가 있습니다." )
                else {

                    if ( alarmRaid() )
                        DefaultNotification.startWeeklyNotification( context, "하지 않은 주간 숙제가 있습니다." )

                }

            }

        }

    }

    private suspend fun alarmDaily(): Boolean {
        Log.i("asdf","++++++++++++++++++START ALARM DAILY+++++++++++++++++++")

        val characterList = repository.getAllCharacterList()

        var result = false

        Log.i("asdf","닉네임 리스트 :: ${characterList.size}")

        for ( character in characterList ) {

            pref.getPref( character.nickName )
            val cantDailyList = repository.getDailyCantCheckList( character.level )
            val dailyList = pref.getDailyList()

            val notiList = pref.getDailyNotiList()
            val notiYesList = notiList.filter { it >= 1 }
            val dailyTotalNotiCount = getNotiYesCount( notiYesList ) - getNotiCount( cantDailyList )

            Log.i("asdf","notiYestList size :: ${notiYesList.size}")
            Log.i("asdf","dailyTotalNotiCount  :: $dailyTotalNotiCount")

            var dailyCheckedCount = 0

            for ( index in 0 until notiList.size ) {

                if ( notiList[index] >= 1 ) {
                    Log.i("asdf","알람 켜놓음 :: $index")
                    dailyCheckedCount += dailyList[index]
                    Log.i("asdf","dailyCheckedCount:: $dailyCheckedCount")


                } else {

                    Log.i("asdf","알람 꺼놓음 :: $index")

                }

            }
            if ( dailyCheckedCount < dailyTotalNotiCount ) {

                result = true
                break

            }

        }
        val calendar = Calendar.getInstance()
        Log.i("asdf","토요일로 되잇는거 꼭 화요일로 바꾸고 출시할것")
        if ( calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            alarmExpedition()

        return result

    }

    private suspend fun alarmWeekly(): Boolean {
        Log.i("asdf","++++++++++++++++++START ALARM WEEKLY+++++++++++++++++++")

        var result = false
        val characterList = repository.getAllCharacterList()

        Log.i("asdf","닉네임 리스트 :: ${characterList.size}")

        for ( character in characterList ) {

            pref.getPref( character.nickName )
            val cantWeeklyList = repository.getWeeklyCantCheckList( character.level )
            val weeklyList = pref.getWeeklyList()

            val notiList = pref.getWeeklyNotiList()
            val notiYesList = notiList.filter { it >= 1 }
            val weeklyTotalNotiCount = getNotiYesCount( notiYesList ) - getNotiCount( cantWeeklyList )

            Log.i("asdf","notiYestList size :: ${notiYesList.size}")
            Log.i("asdf","weeklyTotalNotiCount  :: $weeklyTotalNotiCount")

            var weeklyCheckedCount = 0

            for ( index in 0 until notiList.size ) {

                if ( notiList[index] >= 1 ) {
                    Log.i("asdf","알람 켜놓음 :: $index")
                    weeklyCheckedCount += weeklyList[index]
                    Log.i("asdf","weeklyCheckedCount:: $weeklyCheckedCount")


                } else {

                    Log.i("asdf","알람 꺼놓음 :: $index")

                }

            }
            if ( weeklyCheckedCount < weeklyTotalNotiCount ) {

                result = true
                break

            }

        }
        return result

    }

    private suspend fun alarmRaid(): Boolean {
        Log.i("asdf","++++++++++++++++++START ALARM RAID+++++++++++++++++++")

        var result = false
        val characterList = repository.getAllCharacterList()

        Log.i("asdf","닉네임 리스트 :: ${characterList.size}")

        for ( character in characterList ) {

            pref.getPref( character.nickName )
            val cantRaidList = repository.getRaidCantCheckList( character.level )
            val raidList = pref.getRaidList()

            val notiList = pref.getRaidNotiList()
            val notiYesList = notiList.filter { it >= 1 }
            val raidTotalNotiCount = getNotiYesCount( notiYesList ) - getNotiCount( cantRaidList )

            Log.i("asdf","notiYestList size :: ${notiYesList.size}")
            Log.i("asdf","raidTotalNotiCount  :: $raidTotalNotiCount")

            var raidCheckedCount = 0

            for ( index in 0 until notiList.size ) {

                if ( notiList[index] >= 1 ) {
                    Log.i("asdf","알람 켜놓음 :: $index")
                    raidCheckedCount += raidList[index]
                    Log.i("asdf","raidCheckedCount:: $raidCheckedCount")


                } else {

                    Log.i("asdf","알람 꺼놓음 :: $index")

                }

            }
            if ( raidCheckedCount < raidTotalNotiCount ) {

                result = true
                break

            }

        }
        return result

    }

    private suspend fun alarmExpedition(): Boolean {
        Log.i("asdf","++++++++++++++++++START ALARM EXPEDITION+++++++++++++++++++")

        var result = false
        val characterList = repository.getAllCharacterList()

        Log.i("asdf","닉네임 리스트 :: ${characterList.size}")

        for ( character in characterList ) {

            pref.getPref()
            val cantExpeditionList = repository.getExpeditionCantCheckList( character.level )
            val expeditionList = pref.getExpeditionList()

            val notiList = pref.getExpeditionNotiList()
            val notiYesList = notiList.filter { it >= 1 }
            val expeditionTotalNotiCount = getNotiYesCount( notiYesList ) - getNotiCount( cantExpeditionList )

            Log.i("asdf","notiYestList size :: ${notiYesList.size}")
            Log.i("asdf","expeditionTotalNotiCount  :: $expeditionTotalNotiCount")

            var expeditionCheckedCount = 0

            for ( index in 0 until notiList.size ) {

                if ( notiList[index] >= 1 ) {
                    Log.i("asdf","알람 켜놓음 :: $index")
                    expeditionCheckedCount += expeditionList[index]
                    Log.i("asdf","expeditionCheckedCount:: $expeditionCheckedCount")


                } else {

                    Log.i("asdf","알람 꺼놓음 :: $index")

                }

            }
            if ( expeditionCheckedCount < expeditionTotalNotiCount ) {

                result = true
                break

            }

        }

        return result

    }


    private fun getNotiCount( list: List<CheckListEntity> ): Int {

        var result = 0
        for ( checkList in list ) {

            result += checkList.count

        }
        return result

    }

    private fun getNotiYesCount( list: List<Int> ): Int {

        var result = 0
        for ( count in list ) {

            result += count

        }
        return result
    }

}

