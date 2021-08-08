package com.jh.roachecklist.ui.checklist.weekly

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jh.roachecklist.Const
import com.jh.roachecklist.Const.WeeklyWork
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.ui.checklist.CheckListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeeklyViewModel @Inject constructor(private val pref: AppPreference ): BaseViewModel() {

    private var nickName = ""
    private var level = 0

    var weekly = MutableLiveData( arrayListOf<CheckListModel>(

        CheckListModel( WeeklyWork.CHALLENGE_ABYSS_DUNGEON, 0, null, 0, 0, 1 ),
        CheckListModel( WeeklyWork.CHALLENGE_GUARDIAN, 0, null, 0, 0, 3 ),
        CheckListModel( WeeklyWork.WEEKLY_EFONA, 0, null, 0, 0, 3 ),
        CheckListModel( WeeklyWork.ARGOS_1, 1370, null, 1500, 0, 1 ),
        CheckListModel( WeeklyWork.ARGOS_2, 1385, null, 800, 0, 1 ),
        CheckListModel( WeeklyWork.ARGOS_3, 1400, null, 1000, 0, 1 ),
        CheckListModel( WeeklyWork.GHOST_SHIP, 0, null, 0, 0, 1 ),
        CheckListModel( WeeklyWork.OREHA_NOMAL, 1325, 1415, 1500, 0, 1 ),
        CheckListModel( WeeklyWork.OREHA_HARD, 1355, 1415, 1700, 0, 1 ),
        CheckListModel( WeeklyWork.OREHA_BUS, 1415, null, 0, 0, 1 ),

        ) )
    private fun filterList( level: Int ) {

        weekly.value = weekly.value?.filter { model ->

            level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 10000

        } as ArrayList<CheckListModel>

    }

    private fun setDailyList() {

        val weeklyList = pref.getWeeklyList()
        for ( index in 0 until weeklyList.size ) {
            Log.i("zxcv", "index :: ${index} :: ${weeklyList[index]}")

            weekly.value!![index].checkedCount = weeklyList[index]

        }

    }

    fun setCharacterInfo( nickName: String, level: Int ) {

        this.nickName = nickName
        this.level = level
        pref.getPref( nickName )
        setDailyList()
        filterList ( level )

        for ( work in weekly.value!! ) {

            Log.i("zxcv", "${work.work} checked :: ${work.checkedCount}")

        }

    }

    fun increaseCheckedCount( pos: Int, otherDifficulty: Int? ) {

//        Log.i("zxcv", "work:: $work")


        when ( weekly.value!![pos].work ) {

            WeeklyWork.CHALLENGE_ABYSS_DUNGEON -> {

                pref.challengeAbyssDungeon = pref.challengeAbyssDungeon + 1
                weekly.value!![pos].checkedCount = pref.challengeAbyssDungeon

            }
            WeeklyWork.CHALLENGE_GUARDIAN -> {

                pref.challengeGuardian = pref.challengeGuardian + 1
                weekly.value!![pos].checkedCount = pref.challengeGuardian

            }
            WeeklyWork.WEEKLY_EFONA -> {

                pref.weeklyEfona = pref.weeklyEfona + 1
                weekly.value!![pos].checkedCount = pref.weeklyEfona

            }
            WeeklyWork.ARGOS_1 -> {

                pref.argos1 = pref.argos1 + 1
                weekly.value!![pos].checkedCount = pref.argos1

            }
            WeeklyWork.ARGOS_2 -> {

                pref.argos2 = pref.argos2 + 1
                weekly.value!![pos].checkedCount = pref.argos2

            }
            WeeklyWork.ARGOS_3 -> {

                pref.argos3 = pref.argos3 + 1
                weekly.value!![pos].checkedCount = pref.argos3

            }
            WeeklyWork.GHOST_SHIP -> {

                pref.ghostShip = pref.ghostShip + 1
                weekly.value!![pos].checkedCount = pref.ghostShip

            }
            WeeklyWork.OREHA_NOMAL -> {

                pref.orehaNomal = pref.orehaNomal + 1
                weekly.value!![pos].checkedCount = pref.orehaNomal
                if ( pref.orehaHard == 1 ) {

                    pref.orehaHard = pref.orehaHard - 1
                    otherDifficulty?.let { weekly.value!![it].checkedCount = pref.orehaHard }

                }

            }
            WeeklyWork.OREHA_HARD -> {

                pref.orehaHard = pref.orehaHard + 1
                weekly.value!![pos].checkedCount = pref.orehaHard
                if ( pref.orehaNomal == 1 ) {

                    pref.orehaNomal = pref.orehaNomal - 1
                    otherDifficulty?.let { weekly.value!![it].checkedCount = pref.orehaNomal }

                }

            }
            WeeklyWork.OREHA_BUS -> {

                pref.orehaBus = pref.orehaBus + 1
                weekly.value!![pos].checkedCount = pref.orehaBus

            }


        }

    }

    fun decreaseCheckedCount( pos: Int ) {

        when ( weekly.value!![pos].work ) {

            WeeklyWork.CHALLENGE_ABYSS_DUNGEON -> {

                pref.challengeAbyssDungeon = pref.challengeAbyssDungeon - 1
                weekly.value!![pos].checkedCount = pref.challengeAbyssDungeon

            }
            WeeklyWork.CHALLENGE_GUARDIAN -> {

                pref.challengeGuardian = pref.challengeGuardian - 1
                weekly.value!![pos].checkedCount = pref.challengeGuardian

            }
            WeeklyWork.WEEKLY_EFONA -> {

                pref.weeklyEfona = pref.weeklyEfona - 1
                weekly.value!![pos].checkedCount = pref.weeklyEfona

            }
            WeeklyWork.ARGOS_1 -> {

                pref.argos1 = pref.argos1 - 1
                weekly.value!![pos].checkedCount = pref.argos1

            }
            WeeklyWork.ARGOS_2 -> {

                pref.argos2 = pref.argos2 - 1
                weekly.value!![pos].checkedCount = pref.argos2

            }
            WeeklyWork.ARGOS_3 -> {

                pref.argos3 = pref.argos3 - 1
                weekly.value!![pos].checkedCount = pref.argos3

            }
            WeeklyWork.GHOST_SHIP -> {

                pref.ghostShip = pref.ghostShip - 1
                weekly.value!![pos].checkedCount = pref.ghostShip

            }
            WeeklyWork.OREHA_NOMAL -> {

                pref.orehaNomal = pref.orehaNomal - 1
                weekly.value!![pos].checkedCount = pref.orehaNomal

            }
            WeeklyWork.OREHA_HARD -> {

                pref.orehaHard = pref.orehaHard - 1
                weekly.value!![pos].checkedCount = pref.orehaHard

            }
            WeeklyWork.OREHA_BUS -> {

                pref.orehaBus = pref.orehaBus - 1
                weekly.value!![pos].checkedCount = pref.orehaBus

            }


        }

    }

}