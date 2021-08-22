package com.jh.roachecklist.utils

import android.content.Context
import android.util.Log
import com.jh.roachecklist.Const
import com.jh.roachecklist.db.CharacterEntity
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

                for ( nickName in nickNameList ) {

                    pref.getPref( nickName )
                    val dailyList = pref.getDailyList()

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
                    pref.resetExpeditionDaily()
                    if ( calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY ) {

                        pref.resetWeekly()
                        pref.resetExpeditionWeekly()

                    }

                }

            }

        }

    }

    fun validAlarm() {

        CoroutineScope( Dispatchers.IO ).launch {

            val characterList = repository.getAllCharacterList()

            if ( alarmDaily( characterList) )
                DefaultNotification.startDailyNotification( context, "하지 않은 일일 숙제가 있습니다." )
            else
                if ( alarmExpeditionDaily( characterList ) )
                    DefaultNotification.startDailyNotification( context, "하지 않은 일일 숙제가 있습니다." )

            val calendar = Calendar.getInstance()

            if ( calendar.get( Calendar.DAY_OF_WEEK ) == Calendar.TUESDAY ) {

                if ( alarmExpeditionWeekly( characterList ) )
                    DefaultNotification.startWeeklyNotification( context, "하지 않은 주간 숙제가 있습니다." )
                else {

                    if ( alarmWeekly( characterList ) )
                        DefaultNotification.startWeeklyNotification( context, "하지 않은 주간 숙제가 있습니다." )
                    else {

                        if ( alarmRaid( characterList ) )
                            DefaultNotification.startWeeklyNotification( context, "하지 않은 주간 숙제가 있습니다." )

                    }

                }

            }

        }

    }

    fun alarmDaily( characterList: List<CharacterEntity> ): Boolean {

        var result = false

        for ( character in characterList ) {

            pref.getPref( character.nickName )
            val cantDailyList = repository.getDailyCantCheckList( character.level )
            val dailyList = pref.getDailyList()

            val notiList = pref.getDailyNotiList()
            val notiYesList = notiList.filter { it >= 1 }
            val dailyTotalNotiCount = getNotiYesCount( notiYesList ) - getNotiCount( cantDailyList )

            var dailyCheckedCount = 0

            for ( index in 0 until notiList.size ) {

                if ( notiList[index] >= 1 ) {

                    dailyCheckedCount += dailyList[index]

                }

            }
            if ( dailyCheckedCount < dailyTotalNotiCount ) {
                result = true
                break

            }

        }

        return result

    }

    fun alarmWeekly( characterList: List<CharacterEntity> ): Boolean {

        var result = false

        for ( character in characterList ) {

            pref.getPref( character.nickName )
            val cantWeeklyList = repository.getWeeklyCantCheckList( character.level )
            val weeklyList = pref.getWeeklyList()

            val notiList = pref.getWeeklyNotiList()
            val notiYesList = notiList.filter { it >= 1 }
            val weeklyTotalNotiCount = getNotiYesCount( notiYesList ) - getNotiCount( cantWeeklyList )

            var weeklyCheckedCount = 0

            for ( index in 0 until notiList.size ) {

                if ( notiList[index] >= 1 ) {

                    weeklyCheckedCount += weeklyList[index]

                }

            }
            if ( weeklyCheckedCount < weeklyTotalNotiCount ) {

                result = true
                break

            }

        }
        return result

    }

    fun alarmRaid( characterList: List<CharacterEntity> ): Boolean {

        var result = false

        for ( character in characterList ) {

            pref.getPref( character.nickName )
            val cantRaidList = repository.getRaidCantCheckList( character.level )
            val raidList = pref.getRaidList()

            val notiList = pref.getRaidNotiList()
            val notiYesList = notiList.filter { it >= 1 }
            val raidTotalNotiCount = getNotiYesCount( notiYesList ) - getNotiCount( cantRaidList )

            var raidCheckedCount = 0

            for ( index in 0 until notiList.size ) {

                if ( notiList[index] >= 1 ) {

                    raidCheckedCount += raidList[index]

                }

            }
            if ( raidCheckedCount < raidTotalNotiCount ) {

                result = true
                break

            }

        }
        return result

    }

    private fun alarmExpeditionDaily( characterList: List<CharacterEntity> ): Boolean {

        var result = false

        for ( character in characterList ) {

            pref.getPref()
            val cantExpeditionList = repository.getExpeditionDailyCantCheckList( character.level )
            val expeditionList = pref.getExpeditionDailyList()

            val notiList = pref.getExpeditionDailyNotiList()
            val notiYesList = notiList.filter { it >= 1 }
            val expeditionTotalNotiCount = getNotiYesCount( notiYesList ) - getNotiCount( cantExpeditionList )

            var expeditionCheckedCount = 0

            for ( index in 0 until notiList.size ) {

                if ( notiList[index] >= 1 ) {

                    expeditionCheckedCount += expeditionList[index]

                }

            }
            if ( expeditionCheckedCount < expeditionTotalNotiCount ) {

                result = true
                break

            }

        }

        return result

    }

    private fun alarmExpeditionWeekly( characterList: List<CharacterEntity> ): Boolean {

        var result = false

        for ( character in characterList ) {

            pref.getPref()
            val cantExpeditionList = repository.getExpeditionWeeklyCantCheckList( character.level )
            val expeditionList = pref.getExpeditionWeeklyList()

            val notiList = pref.getExpeditionWeeklyNotiList()
            val notiYesList = notiList.filter { it >= 1 }
            val expeditionTotalNotiCount = getNotiYesCount( notiYesList ) - getNotiCount( cantExpeditionList )

            var expeditionCheckedCount = 0

            for ( index in 0 until notiList.size ) {

                if ( notiList[index] >= 1 ) {

                    expeditionCheckedCount += expeditionList[index]

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

