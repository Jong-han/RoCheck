package com.jh.roachecklist.ui.checklist.expedition.daily

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jh.roachecklist.Const
import com.jh.roachecklist.Const.ExpeditionDaily
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
class ExpeditionDailyViewModel  @Inject constructor( private val repository: Repository,
                                                     private val pref: AppPreference ): BaseViewModel() {

    var level = 0

    var expeditionDaily = MutableLiveData<List<CheckListEntity>>()

    private fun filterList( level: Int ) {

        expeditionDaily.value = expeditionDaily.value?.filter { model ->

            level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 10000

        } as ArrayList<CheckListEntity>

    }

    private fun setExpeditionList() {

        val expeditionDailyList = pref.getExpeditionDailyList()
        val expeditionDailyNotiList = pref.getExpeditionDailyNotiList()

        for ( index in 0 until expeditionDailyList.size ) {

            expeditionDaily.value!![index].checkedCount = expeditionDailyList[index]
            expeditionDaily.value!![index].isNoti = expeditionDailyNotiList[index]

        }

    }

    fun setCharacterInfo( ) {

        pref.getPref()

        viewModelScope.launch( Dispatchers.IO ) {

            level = repository.getHighestLevel() ?: 0

            val result = repository.getExpeditionDailyCheckList()
            withContext( Dispatchers.Main ) {

                expeditionDaily.value = result
                setExpeditionList()
                filterList ( level )

            }

        }

    }

    fun increaseCheckedCount( pos: Int ) {

        when ( expeditionDaily.value!![pos].work ) {

            ExpeditionDaily.FAVORABILITY -> {

                pref.favorability = pref.favorability + 1
                expeditionDaily.value!![pos].checkedCount = pref.favorability

            }

            ExpeditionDaily.FIELD_BOSS -> {

                pref.fieldBoss = pref.fieldBoss + 1
                expeditionDaily.value!![pos].checkedCount = pref.fieldBoss

            }

            ExpeditionDaily.ISLAND -> {

                pref.island = pref.island + 1
                expeditionDaily.value!![pos].checkedCount = pref.island

            }

            ExpeditionDaily.CHAOS_GATE -> {

                pref.chaosGate = pref.chaosGate + 1
                expeditionDaily.value!![pos].checkedCount = pref.chaosGate

            }


        }

    }

    fun decreaseCheckedCount( pos: Int ) {

        when ( expeditionDaily.value!![pos].work ) {

            ExpeditionDaily.FAVORABILITY -> {

                pref.favorability = pref.favorability - 1
                expeditionDaily.value!![pos].checkedCount = pref.favorability

            }

            ExpeditionDaily.FIELD_BOSS -> {

                pref.fieldBoss = pref.fieldBoss - 1
                expeditionDaily.value!![pos].checkedCount = pref.fieldBoss

            }

            ExpeditionDaily.ISLAND -> {

                pref.island = pref.island - 1
                expeditionDaily.value!![pos].checkedCount = pref.island

            }

            ExpeditionDaily.CHAOS_GATE -> {

                pref.chaosGate = pref.chaosGate - 1
                expeditionDaily.value!![pos].checkedCount = pref.chaosGate

            }


        }

    }

    fun onClickNoti( pos: Int ) {

        when ( expeditionDaily.value!![pos].work ) {

            ExpeditionDaily.FAVORABILITY -> {

                if ( pref.favorabilityNoti >= Const.NotiState.YES )
                    pref.favorabilityNoti = Const.NotiState.NO
                else
                    pref.favorabilityNoti = Const.NotiState.YES

                expeditionDaily.value!![pos].isNoti = pref.favorabilityNoti

            }
            ExpeditionDaily.FIELD_BOSS -> {

                if ( pref.fieldBossNoti >= Const.NotiState.YES )
                    pref.fieldBossNoti = Const.NotiState.NO
                else
                    pref.fieldBossNoti = Const.NotiState.YES

                expeditionDaily.value!![pos].isNoti = pref.fieldBossNoti

            }
            ExpeditionDaily.CHAOS_GATE -> {

                if ( pref.chaosGateNoti >= Const.NotiState.YES )
                    pref.chaosGateNoti = Const.NotiState.NO
                else
                    pref.chaosGateNoti = Const.NotiState.YES

                expeditionDaily.value!![pos].isNoti = pref.chaosGateNoti

            }
            ExpeditionDaily.ISLAND -> {

                if ( pref.islandNoti >= Const.NotiState.YES )
                    pref.islandNoti = Const.NotiState.NO
                else
                    pref.islandNoti = Const.NotiState.YES

                expeditionDaily.value!![pos].isNoti = pref.islandNoti

            }

        }

    }

}