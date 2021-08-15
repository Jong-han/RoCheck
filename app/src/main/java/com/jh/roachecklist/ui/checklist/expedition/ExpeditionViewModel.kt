package com.jh.roachecklist.ui.checklist.expedition

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jh.roachecklist.Const
import com.jh.roachecklist.Const.Expedition
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
class ExpeditionViewModel @Inject constructor( private val repository: Repository,
                                               private val pref: AppPreference,
                                               savedState: SavedStateHandle): BaseViewModel() {

    private var level = 0

    var expedition = MutableLiveData<List<CheckListEntity>>()

    private fun filterList( level: Int ) {

        expedition.value = expedition.value?.filter { model ->

            level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 10000

        } as ArrayList<CheckListEntity>

    }

    private fun setExpeditionList() {

        val expeditionList = pref.getExpeditionList()
        val expeditionNotiList = pref.getExpeditionNotiList()

        for ( index in 0 until expeditionList.size ) {

            expedition.value!![index].checkedCount = expeditionList[index]
            expedition.value!![index].isNoti = expeditionNotiList[index]

        }

    }

    fun setCharacterInfo( ) {

        pref.getPref()

        viewModelScope.launch( Dispatchers.IO ) {

            level = repository.getHighestLevel() ?: 0

            val result =  repository.getExpeditionCheckList()
            withContext( Dispatchers.Main ) {

                expedition.value = result
                setExpeditionList()
                filterList ( level )

            }

        }

    }

    fun increaseCheckedCount( pos: Int ) {

        when ( expedition.value!![pos].work ) {

            Expedition.CHALLENGE_ABYSS_DUNGEON -> {

                pref.challengeAbyssDungeon = pref.challengeAbyssDungeon + 1
                expedition.value!![pos].checkedCount = pref.challengeAbyssDungeon

            }

            Expedition.KOUKUSATON_REHEARSAL -> {

                pref.koukusatonRehearsal = pref.koukusatonRehearsal + 1
                expedition.value!![pos].checkedCount = pref.koukusatonRehearsal

            }

            Expedition.ABRELSHOULD_DEJAVU -> {

                pref.abrelshouldDevaju = pref.abrelshouldDevaju + 1
                expedition.value!![pos].checkedCount = pref.abrelshouldDevaju

            }


        }

    }

    fun decreaseCheckedCount( pos: Int ) {

        when ( expedition.value!![pos].work ) {

            Expedition.CHALLENGE_ABYSS_DUNGEON -> {

                pref.challengeAbyssDungeon = pref.challengeAbyssDungeon -1
                expedition.value!![pos].checkedCount = pref.challengeAbyssDungeon

            }

            Expedition.KOUKUSATON_REHEARSAL -> {

                pref.koukusatonRehearsal = pref.koukusatonRehearsal - 1
                expedition.value!![pos].checkedCount = pref.koukusatonRehearsal

            }

            Expedition.ABRELSHOULD_DEJAVU -> {

                pref.abrelshouldDevaju = pref.abrelshouldDevaju - 1
                expedition.value!![pos].checkedCount = pref.abrelshouldDevaju

            }


        }

    }

    fun onClickNoti( pos: Int ) {

        when ( expedition.value!![pos].work ) {

            Expedition.CHALLENGE_ABYSS_DUNGEON -> {

                if ( pref.abyssDungeonNoti >= Const.NotiState.YES )
                    pref.abyssDungeonNoti = Const.NotiState.NO
                else
                    pref.abyssDungeonNoti = Const.NotiState.YES

                expedition.value!![pos].isNoti = pref.abyssDungeonNoti

            }
            Expedition.ABRELSHOULD_DEJAVU -> {

                if ( pref.abrelshouldDevajuNoti >= Const.NotiState.YES )
                    pref.abrelshouldDevajuNoti = Const.NotiState.NO
                else
                    pref.abrelshouldDevajuNoti = Const.NotiState.YES
                expedition.value!![pos].isNoti = pref.abrelshouldDevajuNoti

            }
            Expedition.KOUKUSATON_REHEARSAL -> {

                if ( pref.koukosatonRehearsalNoti >= Const.NotiState.YES )
                    pref.koukosatonRehearsalNoti = Const.NotiState.NO
                else
                    pref.koukosatonRehearsalNoti = Const.NotiState.YES
                expedition.value!![pos].isNoti = pref.koukosatonRehearsalNoti

            }

        }

    }

}