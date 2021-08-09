package com.jh.roachecklist.ui.checklist.daily

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jh.roachecklist.Const
import com.jh.roachecklist.Const.DailyIndex
import com.jh.roachecklist.Const.DailyWork
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.ui.checklist.CheckListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor( private val pref: AppPreference ): BaseViewModel() {

    private var nickName = ""
    private var level = 0
    private var dailyMap: HashMap<String, Int>? =  null

    var daily = MutableLiveData( arrayListOf<CheckListModel>(
        CheckListModel( DailyWork.GUILD, 0, null, 0, 0, 1 ),
        CheckListModel( DailyWork.DAILY_EFONA, 0, null, 0, 0, 3),
        CheckListModel( DailyWork.FAVORABILITY, 0, null, 0, 0, 1),
        CheckListModel( DailyWork.ISLAND, 0, null, 0, 0, 1 ),
        CheckListModel( DailyWork.FIELD_BOSS, 0, null, 0, 0, 2 ),
        CheckListModel( DailyWork.DAILY_GUARDIAN, 0, null, 0, 0, 2 ),
        CheckListModel( DailyWork.CHAOS_GATE, 0, null, 0, 0, 1 ),
        CheckListModel( DailyWork.CHAOS_DUNGEON, 0, null, 0, 0, 2 ),
    ))

    private fun filterList(level: Int ) {

        daily.value = daily.value?.filter { model ->

            level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 10000

        } as ArrayList<CheckListModel>

    }

    private fun setDailyList() {

        val dailyList = pref.getDailyList()
        for ( index in 0 until dailyList.size ) {

            daily.value!![index].checkedCount = dailyList[index]

        }

    }

    fun setCharacterInfo( nickName: String, level: Int ) {

        this.nickName = nickName
        this.level = level
        pref.getPref( nickName )
        filterList ( level )
        setDailyList()

        for ( work in daily.value!! ) {

            Log.i("zxcv", "${work.work} checked :: ${work.checkedCount}")

        }

    }

    fun increaseCheckedCount( pos: Int ) {

        when ( daily.value!![pos].work ) {

            DailyWork.GUILD -> {

                pref.guild = pref.guild + 1
                daily.value!![pos].checkedCount = pref.guild
            }
            DailyWork.DAILY_EFONA -> {

                pref.dailyEfona = pref.dailyEfona + 1
                daily.value!![pos].checkedCount = pref.dailyEfona

            }
            DailyWork.FAVORABILITY -> {

                pref.favorability = pref.favorability + 1
                daily.value!![pos].checkedCount = pref.favorability

            }
            DailyWork.ISLAND -> {

                pref.island = pref.island + 1
                daily.value!![pos].checkedCount = pref.island

            }
            DailyWork.FIELD_BOSS -> {

                pref.fieldBoss = pref.fieldBoss + 1
                daily.value!![pos].checkedCount = pref.fieldBoss

            }
            DailyWork.DAILY_GUARDIAN -> {

                pref.dailyGuardian = pref.dailyGuardian + 1
                daily.value!![pos].checkedCount = pref.dailyGuardian

            }
            DailyWork.CHAOS_GATE -> {

                pref.chaosGate = pref.chaosGate + 1
                daily.value!![pos].checkedCount = pref.chaosGate

            }
            DailyWork.CHAOS_DUNGEON -> {

                pref.chaosDungeon = pref.chaosDungeon + 1
                daily.value!![pos].checkedCount = pref.chaosDungeon

            }

        }

    }

    fun decreaseCheckedCount( pos: Int ) {

        when ( daily.value!![pos].work ) {

            DailyWork.GUILD -> {

                pref.guild = pref.guild - 1
                daily.value!![pos].checkedCount = pref.guild
            }
            DailyWork.DAILY_EFONA -> {

                pref.dailyEfona = pref.dailyEfona - 1
                daily.value!![pos].checkedCount = pref.dailyEfona

            }
            DailyWork.FAVORABILITY -> {

                pref.favorability = pref.favorability - 1
                daily.value!![pos].checkedCount = pref.favorability

            }
            DailyWork.ISLAND -> {

                pref.island = pref.island - 1
                daily.value!![pos].checkedCount = pref.island

            }
            DailyWork.FIELD_BOSS -> {

                pref.fieldBoss = pref.fieldBoss - 1
                daily.value!![pos].checkedCount = pref.fieldBoss

            }
            DailyWork.DAILY_GUARDIAN -> {

                pref.dailyGuardian = pref.dailyGuardian - 1
                daily.value!![pos].checkedCount = pref.dailyGuardian

            }
            DailyWork.CHAOS_GATE -> {

                pref.chaosGate = pref.chaosGate - 1
                daily.value!![pos].checkedCount = pref.chaosGate

            }
            DailyWork.CHAOS_DUNGEON -> {

                pref.chaosDungeon = pref.chaosDungeon - 1
                daily.value!![pos].checkedCount = pref.chaosDungeon

            }

        }

    }




}