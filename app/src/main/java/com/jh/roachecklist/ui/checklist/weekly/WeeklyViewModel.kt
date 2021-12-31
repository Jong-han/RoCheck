package com.jh.roachecklist.ui.checklist.weekly

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jh.roachecklist.Const
import com.jh.roachecklist.Const.WeeklyWork
import com.jh.roachecklist.db.CheckListEntity
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.repository.Repository
import com.jh.roachecklist.ui.base.BaseViewModel
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

        when ( weekly.value!![pos].work ) {

            WeeklyWork.WEEKLY_EFONA -> {

                pref.weeklyEfona = pref.weeklyEfona + 1
                weekly.value!![pos].checkedCount = pref.weeklyEfona

            }
            WeeklyWork.ARGOS -> {

                pref.argos = pref.argos + 1
                weekly.value!![pos].checkedCount = pref.argos

            }

            WeeklyWork.OREHA -> {

                pref.oreha = pref.oreha + 1
                weekly.value!![pos].checkedCount = pref.oreha

            }

        }

    }

    fun decreaseCheckedCount( pos: Int ) {

        when ( weekly.value!![pos].work ) {

            WeeklyWork.WEEKLY_EFONA -> {

                pref.weeklyEfona = pref.weeklyEfona - 1
                weekly.value!![pos].checkedCount = pref.weeklyEfona

            }
            WeeklyWork.ARGOS -> {

                pref.argos = pref.argos - 1
                weekly.value!![pos].checkedCount = pref.argos

            }
            WeeklyWork.OREHA -> {

                pref.oreha = pref.oreha - 1
                weekly.value!![pos].checkedCount = pref.oreha

            }

        }

    }

    fun onClickNoti( pos: Int ) {

        when ( weekly.value!![pos].work ) {

            Const.WeeklyWork.WEEKLY_EFONA -> {

                if ( pref.weeklyEfonaNoti >= Const.NotiState.YES )
                    pref.weeklyEfonaNoti = Const.NotiState.NO
                else
                    pref.weeklyEfonaNoti = Const.NotiState.YES

                weekly.value!![pos].isNoti = pref.weeklyEfonaNoti

            }

            Const.WeeklyWork.ARGOS -> {

                if ( pref.argosNoti >= Const.NotiState.YES )
                    pref.argosNoti = Const.NotiState.NO
                else
                    pref.argosNoti = Const.NotiState.YES

                weekly.value!![pos].isNoti = pref.argosNoti

            }
            Const.WeeklyWork.OREHA -> {

                if ( pref.orehaNoti >= Const.NotiState.YES )
                    pref.orehaNoti = Const.NotiState.NO
                else
                    pref.orehaNoti = Const.NotiState.YES

                weekly.value!![pos].isNoti = pref.orehaNoti

            }

        }

    }

}