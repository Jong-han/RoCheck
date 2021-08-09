package com.jh.roachecklist.ui.checklist.expedition

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.jh.roachecklist.Const
import com.jh.roachecklist.Const.Expedition
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.ui.character.CharacterActivity
import com.jh.roachecklist.ui.checklist.CheckListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpeditionViewModel @Inject constructor( private val pref: AppPreference, savedState: SavedStateHandle): BaseViewModel() {

    val level = savedState.get<Int>(CharacterActivity.EXTRA_HIGHEST_LEVEL) ?: 0

    var expediton = MutableLiveData( arrayListOf<CheckListModel>(

        CheckListModel( Expedition.CHALLENGE_ABYSS_DUNGEON, 0, null, 0, 0, 1 ),
        CheckListModel( Expedition.KOUKUSATON_REHEARSAL, 1385, null, 0, 0, 1 ),
        CheckListModel( Expedition.ABRELSHOULD_DEJAVU, 1430, null, 0, 0, 1 )

        ) )

    private fun filterList( level: Int ) {

        expediton.value = expediton.value?.filter { model ->

            level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 10000

        } as ArrayList<CheckListModel>

    }

    private fun setExpeditionList() {

        val expeditionList = pref.getExpeditionList()
        for ( index in 0 until expeditionList.size ) {
            Log.i("zxcv", "index :: ${index} :: ${expeditionList[index]}")

            expediton.value!![index].checkedCount = expeditionList[index]

        }

    }

    fun setCharacterInfo( ) {

        pref.getPref()
        setExpeditionList()
        filterList ( level )

        for ( work in expediton.value!! ) {

            Log.i("zxcv", "${work.work} checked :: ${work.checkedCount}")

        }

    }

    fun increaseCheckedCount( pos: Int ) {

//        Log.i("zxcv", "work:: $work")


        when ( expediton.value!![pos].work ) {

            Expedition.CHALLENGE_ABYSS_DUNGEON -> {

                pref.challengeAbyssDungeon = pref.challengeAbyssDungeon + 1
                expediton.value!![pos].checkedCount = pref.challengeAbyssDungeon

            }

            Expedition.KOUKUSATON_REHEARSAL -> {

                pref.koukusatonRehearsal = pref.koukusatonRehearsal + 1
                expediton.value!![pos].checkedCount = pref.koukusatonRehearsal

            }

            Expedition.ABRELSHOULD_DEJAVU -> {

                pref.abrelshouldDevaju = pref.abrelshouldDevaju + 1
                expediton.value!![pos].checkedCount = pref.abrelshouldDevaju

            }


        }

    }

    fun decreaseCheckedCount( pos: Int ) {

//        Log.i("zxcv", "work:: $work")


        when ( expediton.value!![pos].work ) {

            Expedition.CHALLENGE_ABYSS_DUNGEON -> {

                pref.challengeAbyssDungeon = pref.challengeAbyssDungeon -1
                expediton.value!![pos].checkedCount = pref.challengeAbyssDungeon

            }

            Expedition.KOUKUSATON_REHEARSAL -> {

                pref.koukusatonRehearsal = pref.koukusatonRehearsal - 1
                expediton.value!![pos].checkedCount = pref.koukusatonRehearsal

            }

            Expedition.ABRELSHOULD_DEJAVU -> {

                pref.abrelshouldDevaju = pref.abrelshouldDevaju - 1
                expediton.value!![pos].checkedCount = pref.abrelshouldDevaju

            }


        }

    }
}