package com.jh.roachecklist.ui.checklist.daily

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jh.roachecklist.Const
import com.jh.roachecklist.Const.DailyIndex
import com.jh.roachecklist.Const.DailyWork
import com.jh.roachecklist.db.CheckListEntity
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.repository.Repository
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor( private val pref: AppPreference, private val repository: Repository ): BaseViewModel() {

    private var nickName = ""
    private var level = 0

    val clickSettingRest = SingleLiveEvent<Any>()

    val displayEfonaRestBonus = MutableLiveData<Int>(0)
    val displayChaosRestBonus = MutableLiveData<Int>(0)
    val displayGuardianRestBonus = MutableLiveData<Int>(0)

    private var displayEfonaRestBonusFirst = 0
    private var displayChaosRestBonusFirst = 0
    private var displayGuardianRestBonusFirst = 0

    var daily = MutableLiveData<List<CheckListEntity>>( )

    fun clickSettingRest() {

        clickSettingRest.call()

    }

    private fun filterList(level: Int ) {

        daily.value = daily.value?.filter { model ->

            level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 10000

        } as ArrayList<CheckListEntity>

    }

    private fun setDailyList() {

        val dailyList = pref.getDailyList()
        val dailyNotiList = pref.getDailyNotiList()
        for ( index in 0 until dailyList.size ) {

            daily.value!![index].checkedCount = dailyList[index]
            daily.value!![index].isNoti = dailyNotiList[index]

            when ( index ) {

                DailyIndex.DAILY_EFONA -> {

                    when {
                        displayEfonaRestBonusFirst >= Const.Rest.CONSUME_REST_BONUS * dailyList[index] -> displayEfonaRestBonus.value = displayEfonaRestBonus.value?.minus( Const.Rest.CONSUME_REST_BONUS * dailyList[index] )
                        displayEfonaRestBonusFirst >= Const.Rest.CONSUME_REST_BONUS * ( dailyList[index] - 1 ) -> displayEfonaRestBonus.value = displayEfonaRestBonus.value?.minus( Const.Rest.CONSUME_REST_BONUS  * ( dailyList[index] - 1 ) )
                        displayEfonaRestBonusFirst >= Const.Rest.CONSUME_REST_BONUS * ( dailyList[index] - 2 ) -> displayEfonaRestBonus.value = displayEfonaRestBonus.value?.minus( Const.Rest.CONSUME_REST_BONUS  * ( dailyList[index] - 2 ) )
                        displayEfonaRestBonusFirst >= Const.Rest.CONSUME_REST_BONUS * ( dailyList[index] - 3 ) -> displayEfonaRestBonus.value = displayEfonaRestBonus.value?.minus( Const.Rest.CONSUME_REST_BONUS  * ( dailyList[index] - 3 ) )
                    }

                }
                DailyIndex.CHAOS_DUNGEON -> {

                    when {
                        displayChaosRestBonusFirst >= Const.Rest.CONSUME_REST_BONUS * dailyList[index] -> displayChaosRestBonus.value = displayChaosRestBonus.value?.minus( Const.Rest.CONSUME_REST_BONUS * dailyList[index] )
                        displayChaosRestBonusFirst >= Const.Rest.CONSUME_REST_BONUS * ( dailyList[index] - 1 ) -> displayChaosRestBonus.value = displayChaosRestBonus.value?.minus( Const.Rest.CONSUME_REST_BONUS  * ( dailyList[index] - 1 ) )
                        displayChaosRestBonusFirst >= Const.Rest.CONSUME_REST_BONUS * ( dailyList[index] - 2 ) -> displayChaosRestBonus.value = displayChaosRestBonus.value?.minus( Const.Rest.CONSUME_REST_BONUS  * ( dailyList[index] - 2 ) )
                    }

                }
                DailyIndex.DAILY_GUARDIAN -> {

                    when {
                        displayGuardianRestBonusFirst >= Const.Rest.CONSUME_REST_BONUS * dailyList[index] -> displayGuardianRestBonus.value = displayGuardianRestBonus.value?.minus( Const.Rest.CONSUME_REST_BONUS * dailyList[index] )
                        displayGuardianRestBonusFirst >= Const.Rest.CONSUME_REST_BONUS * ( dailyList[index] - 1 ) -> displayGuardianRestBonus.value = displayGuardianRestBonus.value?.minus( Const.Rest.CONSUME_REST_BONUS  * ( dailyList[index] - 1 ) )
                        displayGuardianRestBonusFirst >= Const.Rest.CONSUME_REST_BONUS * ( dailyList[index] - 2 ) -> displayGuardianRestBonus.value = displayGuardianRestBonus.value?.minus( Const.Rest.CONSUME_REST_BONUS  * ( dailyList[index] - 2 ) )
                    }

                }

            }

        }

    }

    private fun setRestBonus() {

        displayEfonaRestBonus.value = pref.efonaRestBonus
        displayGuardianRestBonus.value = pref.guardianRestBonus
        displayChaosRestBonus.value = pref.chaosRestBonus

        displayEfonaRestBonusFirst = pref.efonaRestBonus
        displayGuardianRestBonusFirst = pref.guardianRestBonus
        displayChaosRestBonusFirst = pref.chaosRestBonus

    }

    fun setCharacterInfo( nickName: String, level: Int ) {

        this.nickName = nickName
        this.level = level
        pref.getPref( nickName )

        viewModelScope.launch( Dispatchers.IO ) {

            val result =  repository.getDailyCheckList()

            withContext( Dispatchers.Main ) {

                daily.value = result
                filterList( level )
                setRestBonus()
                setDailyList()

            }

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
                if ( displayEfonaRestBonus.value ?: 0 >= Const.Rest.CONSUME_REST_BONUS ) {

                    displayEfonaRestBonus.value = displayEfonaRestBonus.value?.minus( Const.Rest.CONSUME_REST_BONUS )

                }

            }
            DailyWork.DAILY_GUARDIAN -> {

                pref.dailyGuardian = pref.dailyGuardian + 1
                daily.value!![pos].checkedCount = pref.dailyGuardian
                if ( displayGuardianRestBonus.value ?: 0 >= Const.Rest.CONSUME_REST_BONUS ) {

                    displayGuardianRestBonus.value = displayGuardianRestBonus.value?.minus( Const.Rest.CONSUME_REST_BONUS )

                }

            }
            DailyWork.CHAOS_DUNGEON -> {

                pref.chaosDungeon = pref.chaosDungeon + 1
                daily.value!![pos].checkedCount = pref.chaosDungeon
                if ( displayChaosRestBonus.value ?: 0 >= Const.Rest.CONSUME_REST_BONUS ) {

                    displayChaosRestBonus.value = displayChaosRestBonus.value?.minus( Const.Rest.CONSUME_REST_BONUS )

                }

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
                if ( displayEfonaRestBonus.value?.plus( Const.Rest.CONSUME_REST_BONUS ) ?: 0  <= displayEfonaRestBonusFirst) {

                    displayEfonaRestBonus.value = displayEfonaRestBonus.value?.plus( Const.Rest.CONSUME_REST_BONUS )

                }

            }
            DailyWork.DAILY_GUARDIAN -> {

                pref.dailyGuardian = pref.dailyGuardian - 1
                daily.value!![pos].checkedCount = pref.dailyGuardian
                if ( displayGuardianRestBonus.value?.plus( Const.Rest.CONSUME_REST_BONUS ) ?: 0  <= displayGuardianRestBonusFirst) {

                    displayGuardianRestBonus.value = displayGuardianRestBonus.value?.plus( Const.Rest.CONSUME_REST_BONUS )

                }

            }
            DailyWork.CHAOS_DUNGEON -> {

                pref.chaosDungeon = pref.chaosDungeon - 1
                daily.value!![pos].checkedCount = pref.chaosDungeon
                if ( displayChaosRestBonus.value?.plus( Const.Rest.CONSUME_REST_BONUS ) ?: 0  <= displayChaosRestBonusFirst) {

                    displayChaosRestBonus.value = displayChaosRestBonus.value?.plus( Const.Rest.CONSUME_REST_BONUS )

                }

            }

        }

    }

    fun editRestBonus( efona: Int, guardian: Int, chaos: Int) {

        pref.efonaRestBonus = efona
        pref.guardianRestBonus = guardian
        pref.chaosRestBonus = chaos

        setRestBonus()
        setDailyList()

    }

    fun onClickNoti( pos: Int ) {

        when ( daily.value!![pos].work ) {

            DailyWork.GUILD -> {

                if ( pref.guildNoti >= Const.NotiState.YES )
                    pref.guildNoti = Const.NotiState.NO
                else
                    pref.guildNoti = Const.NotiState.YES

                daily.value!![pos].isNoti = pref.guildNoti

            }
            DailyWork.DAILY_EFONA -> {

                if ( pref.dailyEfonaNoti >= Const.NotiState.YES )
                    pref.dailyEfonaNoti = Const.NotiState.NO
                else
                    pref.dailyEfonaNoti = Const.NotiState.YES
                daily.value!![pos].isNoti = pref.dailyEfonaNoti

            }
            DailyWork.DAILY_GUARDIAN -> {

                if ( pref.dailyGuardianNoti >= Const.NotiState.YES )
                    pref.dailyGuardianNoti = Const.NotiState.NO
                else
                    pref.dailyGuardianNoti = Const.NotiState.YES

                daily.value!![pos].isNoti = pref.dailyGuardianNoti

            }
            DailyWork.CHAOS_DUNGEON -> {

                if ( pref.chaosDungeonNoti >= Const.NotiState.YES )
                    pref.chaosDungeonNoti = Const.NotiState.NO
                else
                    pref.chaosDungeonNoti = Const.NotiState.YES

                daily.value!![pos].isNoti = pref.chaosDungeonNoti

            }

        }

    }


}