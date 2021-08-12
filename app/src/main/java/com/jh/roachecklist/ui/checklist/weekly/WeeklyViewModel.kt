package com.jh.roachecklist.ui.checklist.weekly

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jh.roachecklist.Const
import com.jh.roachecklist.Const.WeeklyWork
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.db.CheckListEntity
import com.jh.roachecklist.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeeklyViewModel @Inject constructor(private val pref: AppPreference,
                                          private val repository: Repository ): BaseViewModel() {

    private var nickName = ""
    private var level = 0

    var weekly = MutableLiveData<List<CheckListEntity>>()

    private fun filterList( level: Int ) {

        weekly.value = weekly.value?.filter { model ->

            level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 10000

        } as ArrayList<CheckListEntity>

    }

    private fun setWeeklyList() {

        val weeklyList = pref.getWeeklyList()
        val weeklyNotiList = pref.getWeeklyNotiList()
        for ( index in 0 until weeklyList.size ) {

            weekly.value!![index].checkedCount = weeklyList[index]
            weekly.value!![index].isNoti = weeklyNotiList[index]

        }

    }

    fun setCharacterInfo( nickName: String, level: Int ) {

        this.nickName = nickName
        this.level = level
        pref.getPref( nickName )

        viewModelScope.launch( Dispatchers.IO ) {

            val result =  repository.getWeeklyCheckList()
            withContext( Dispatchers.Main ) {

                weekly.value = result
                setWeeklyList()
                filterList ( level )

            }

        }

    }

    fun increaseCheckedCount( pos: Int, otherDifficulty: Int? ) {

//        Log.i("zxcv", "work:: $work")


        when ( weekly.value!![pos].work ) {

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

        }

    }

    fun decreaseCheckedCount( pos: Int ) {

        when ( weekly.value!![pos].work ) {

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

        }

    }

    fun onClickNoti( pos: Int ) {
        Log.i("zxcv","눌린거 :: ${weekly.value!![pos].work}")

        when ( weekly.value!![pos].work ) {

            Const.WeeklyWork.CHALLENGE_GUARDIAN -> {

                if ( pref.challengeGuardianNoti == Const.NotiState.YES )
                    pref.challengeGuardianNoti = Const.NotiState.NO
                else
                    pref.challengeGuardianNoti = Const.NotiState.YES

                weekly.value!![pos].isNoti = pref.challengeGuardianNoti

            }

            Const.WeeklyWork.WEEKLY_EFONA -> {

                if ( pref.weeklyEfonaNoti == Const.NotiState.YES )
                    pref.weeklyEfonaNoti = Const.NotiState.NO
                else
                    pref.weeklyEfonaNoti = Const.NotiState.YES

                weekly.value!![pos].isNoti = pref.weeklyEfonaNoti

            }

            Const.WeeklyWork.ARGOS_1 -> {

                if ( pref.argos1Noti == Const.NotiState.YES )
                    pref.argos1Noti = Const.NotiState.NO
                else
                    pref.argos1Noti = Const.NotiState.YES

                weekly.value!![pos].isNoti = pref.argos1Noti

            }

            Const.WeeklyWork.ARGOS_2 -> {

                if ( pref.argos2Noti == Const.NotiState.YES )
                    pref.argos2Noti = Const.NotiState.NO
                else
                    pref.argos2Noti = Const.NotiState.YES

                weekly.value!![pos].isNoti = pref.argos2Noti

            }

            Const.WeeklyWork.ARGOS_3 -> {

                if ( pref.argos3Noti == Const.NotiState.YES )
                    pref.argos3Noti = Const.NotiState.NO
                else
                    pref.argos3Noti = Const.NotiState.YES

                weekly.value!![pos].isNoti = pref.argos3Noti

            }

            Const.WeeklyWork.GHOST_SHIP -> {

                if ( pref.ghostShipNoti == Const.NotiState.YES )
                    pref.ghostShipNoti = Const.NotiState.NO
                else
                    pref.ghostShipNoti = Const.NotiState.YES

                weekly.value!![pos].isNoti = pref.ghostShipNoti

            }

            Const.WeeklyWork.OREHA_NOMAL -> {

                if ( pref.orehaNormalNoti == Const.NotiState.YES )
                    pref.orehaNormalNoti = Const.NotiState.NO
                else
                    pref.orehaNormalNoti = Const.NotiState.YES

                weekly.value!![pos].isNoti = pref.orehaNormalNoti

            }

            Const.WeeklyWork.OREHA_HARD -> {

                if ( pref.orehaHardNoti == Const.NotiState.YES )
                    pref.orehaHardNoti = Const.NotiState.NO
                else
                    pref.orehaHardNoti = Const.NotiState.YES

                weekly.value!![pos].isNoti = pref.orehaHardNoti

            }

        }

    }

}