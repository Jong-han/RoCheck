package com.jh.roachecklist.ui.checklist.expedition.weekly

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jh.roachecklist.Const
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
class ExpeditionWeeklyViewModel  @Inject constructor( private val repository: Repository,
                                                      private val pref: AppPreference ): BaseViewModel() {

    var level = 0

    var expeditionWeekly = MutableLiveData<List<CheckListEntity>>()

    private fun filterList( level: Int ) {

        expeditionWeekly.value = expeditionWeekly.value?.filter { model ->

            level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 10000

        } as ArrayList<CheckListEntity>

    }

    private fun setExpeditionList() {

        val expeditionWeeklyList = pref.getExpeditionWeeklyList()
        val expeditionWeeklyNotiList = pref.getExpeditionWeeklyNotiList()

        for ( index in 0 until expeditionWeeklyList.size ) {

            expeditionWeekly.value!![index].checkedCount = expeditionWeeklyList[index]
            expeditionWeekly.value!![index].isNoti = expeditionWeeklyNotiList[index]

        }

    }

    fun setCharacterInfo( ) {

        pref.getPref()

        viewModelScope.launch( Dispatchers.IO ) {

            level = repository.getHighestLevel() ?: 0

            val result = repository.getExpeditionWeeklyCheckList()
            withContext( Dispatchers.Main ) {

                expeditionWeekly.value = result
                setExpeditionList()
                filterList ( level )

            }

        }

    }

    fun increaseCheckedCount( pos: Int ) {

        when ( expeditionWeekly.value!![pos].work ) {

            Const.ExpeditionWeekly.CHALLENGE_GUARDIAN -> {

                pref.challengeGuardian = pref.challengeGuardian + 1
                expeditionWeekly.value!![pos].checkedCount = pref.challengeGuardian

            }

            Const.ExpeditionWeekly.ABRELSHOULD_DEJAVU -> {

                pref.abrelshouldDevaju = pref.abrelshouldDevaju + 1
                expeditionWeekly.value!![pos].checkedCount = pref.abrelshouldDevaju

            }
            Const.ExpeditionWeekly.CHALLENGE_ABYSS_DUNGEON -> {

                pref.challengeAbyssDungeon = pref.challengeAbyssDungeon + 1
                expeditionWeekly.value!![pos].checkedCount = pref.challengeAbyssDungeon

            }
            Const.ExpeditionWeekly.GHOST_SHIP -> {

                pref.ghostShip = pref.ghostShip + 1
                expeditionWeekly.value!![pos].checkedCount = pref.ghostShip

            }
            Const.ExpeditionWeekly.KOUKUSATON_REHEARSAL -> {

                pref.koukusatonRehearsal = pref.koukusatonRehearsal + 1
                expeditionWeekly.value!![pos].checkedCount = pref.koukusatonRehearsal

            }

        }

    }

    fun decreaseCheckedCount( pos: Int ) {

        when ( expeditionWeekly.value!![pos].work ) {

            Const.ExpeditionWeekly.CHALLENGE_GUARDIAN -> {

                pref.challengeGuardian = pref.challengeGuardian - 1
                expeditionWeekly.value!![pos].checkedCount = pref.challengeGuardian

            }

            Const.ExpeditionWeekly.ABRELSHOULD_DEJAVU -> {

                pref.abrelshouldDevaju = pref.abrelshouldDevaju - 1
                expeditionWeekly.value!![pos].checkedCount = pref.abrelshouldDevaju

            }
            Const.ExpeditionWeekly.CHALLENGE_ABYSS_DUNGEON -> {

                pref.challengeAbyssDungeon = pref.challengeAbyssDungeon - 1
                expeditionWeekly.value!![pos].checkedCount = pref.challengeAbyssDungeon

            }
            Const.ExpeditionWeekly.GHOST_SHIP -> {

                pref.ghostShip = pref.ghostShip - 1
                expeditionWeekly.value!![pos].checkedCount = pref.ghostShip

            }
            Const.ExpeditionWeekly.KOUKUSATON_REHEARSAL -> {

                pref.koukusatonRehearsal = pref.koukusatonRehearsal - 1
                expeditionWeekly.value!![pos].checkedCount = pref.koukusatonRehearsal

            }

        }

    }

    fun onClickNoti( pos: Int ) {

        when ( expeditionWeekly.value!![pos].work ) {

            Const.ExpeditionWeekly.CHALLENGE_GUARDIAN -> {

                if ( pref.challengeGuardianNoti >= Const.NotiState.YES )
                    pref.challengeGuardianNoti = Const.NotiState.NO
                else
                    pref.challengeGuardianNoti = Const.NotiState.YES

                expeditionWeekly.value!![pos].isNoti = pref.challengeGuardianNoti

            }

            Const.ExpeditionWeekly.KOUKUSATON_REHEARSAL -> {

                if ( pref.koukosatonRehearsalNoti >= Const.NotiState.YES )
                    pref.koukosatonRehearsalNoti = Const.NotiState.NO
                else
                    pref.koukosatonRehearsalNoti = Const.NotiState.YES

                expeditionWeekly.value!![pos].isNoti = pref.koukosatonRehearsalNoti

            }
            Const.ExpeditionWeekly.ABRELSHOULD_DEJAVU -> {

                if ( pref.abrelshouldDevajuNoti >= Const.NotiState.YES )
                    pref.abrelshouldDevajuNoti = Const.NotiState.NO
                else
                    pref.abrelshouldDevajuNoti = Const.NotiState.YES

                expeditionWeekly.value!![pos].isNoti = pref.abrelshouldDevajuNoti

            }
            Const.ExpeditionWeekly.GHOST_SHIP -> {

                if ( pref.ghostShipNoti >= Const.NotiState.YES )
                    pref.ghostShipNoti = Const.NotiState.NO
                else
                    pref.ghostShipNoti = Const.NotiState.YES

                expeditionWeekly.value!![pos].isNoti = pref.ghostShipNoti

            }
            Const.ExpeditionWeekly.CHALLENGE_ABYSS_DUNGEON -> {

                if ( pref.abyssDungeonNoti >= Const.NotiState.YES )
                    pref.abyssDungeonNoti = Const.NotiState.NO
                else
                    pref.abyssDungeonNoti = Const.NotiState.YES

                expeditionWeekly.value!![pos].isNoti = pref.abyssDungeonNoti

            }

        }

    }

}