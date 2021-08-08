package com.jh.roachecklist.ui.checklist.daily

import android.util.Log
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

    var daily = arrayListOf<CheckListModel>(
        CheckListModel( DailyWork.GUILD, 0, null, 0, 0, 1 ),
        CheckListModel( DailyWork.DAILY_EFONA, 0, null, 0, 0, 3),
        CheckListModel( DailyWork.FAVORABILITY, 0, null, 0, 0, 1),
        CheckListModel( DailyWork.ISLAND, 0, null, 0, 0, 1 ),
        CheckListModel( DailyWork.FIELD_BOSS, 0, null, 0, 0, 2 ),
        CheckListModel( DailyWork.DAILY_GUARDIAN, 0, null, 0, 0, 2 ),
        CheckListModel( DailyWork.CHAOS_GATE, 0, null, 0, 0, 1 ),
        CheckListModel( DailyWork.CHAOS_DUNGEON, 0, null, 0, 0, 2 ),
    )

    private fun filterList(level: Int ) {

        daily = daily.filter { model ->

            level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 10000

        } as ArrayList<CheckListModel>

    }

    private fun setDailyList() {

        val dailyList = pref.getDailyList()
        for ( index in 0 until dailyList.size ) {

            daily[index].checkedCount = dailyList[index]

        }

    }

    fun setCharacterInfo( nickName: String, level: Int ) {

        this.nickName = nickName
        this.level = level
        pref.getPref( nickName )
        filterList ( level )
        setDailyList()

        for ( work in daily ) {

            Log.i("zxcv", "${work.work} checked :: ${work.checkedCount}")

        }

    }

    fun increaseCheckedCount( work: String ) {

        when ( work ) {

            DailyWork.GUILD -> pref.guild = pref.guild + 1
            DailyWork.DAILY_EFONA -> pref.dailyEfona = pref.dailyEfona + 1
            DailyWork.FAVORABILITY -> pref.favorability = pref.favorability + 1
            DailyWork.ISLAND -> pref.island = pref.island + 1
            DailyWork.FIELD_BOSS -> pref.fieldBoss = pref.fieldBoss + 1
            DailyWork.DAILY_GUARDIAN -> pref.dailyGuardian = pref.dailyGuardian + 1
            DailyWork.CHAOS_GATE -> pref.chaosGate = pref.chaosGate + 1
            DailyWork.CHAOS_DUNGEON -> pref.chaosDungeon = pref.chaosDungeon + 1


        }

    }

    fun decreaseCheckedCount( work: String ) {

        when ( work ) {

            DailyWork.GUILD -> pref.guild = pref.guild - 1
            DailyWork.DAILY_EFONA -> pref.dailyEfona = pref.dailyEfona - 1
            DailyWork.FAVORABILITY -> pref.favorability = pref.favorability - 1
            DailyWork.ISLAND -> pref.island = pref.island - 1
            DailyWork.FIELD_BOSS -> pref.fieldBoss = pref.fieldBoss - 1
            DailyWork.DAILY_GUARDIAN -> pref.dailyGuardian = pref.dailyGuardian - 1
            DailyWork.CHAOS_GATE -> pref.chaosGate = pref.chaosGate - 1
            DailyWork.CHAOS_DUNGEON -> pref.chaosDungeon = pref.chaosDungeon - 1


        }

    }




}