package com.jh.roachecklist.ui.checklist.raid

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jh.roachecklist.Const
import com.jh.roachecklist.Const.Raid
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.ui.checklist.CheckListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RaidViewModel @Inject constructor( private val pref: AppPreference ): BaseViewModel() {

    private var nickName = ""
    private var level = 0

    var raid = MutableLiveData( arrayListOf<CheckListModel>(
        CheckListModel( Raid.BALTAN_NORMAL, 1415, null, 3300, 0, 1 ),
        CheckListModel( Raid.BALRAN_HARD, 1445, null, 4500, 0, 1 ),
        CheckListModel( Raid.VIAKISS_NORMAL, 1430, null, 3300, 0, 1 ),
        CheckListModel( Raid.VIAKISS_HARD, 1460, null, 4500, 0, 1 ),
        CheckListModel( Raid.KOUKUSATON_REHEARSAL, 1385, null, 0, 0, 1 ),
        CheckListModel( Raid.KOUKUSATON_NORMAL, 1475, null, 0, 0, 1 ),
        CheckListModel( Raid.ABRELSHOULD_DEJAVU, 1430, null, 0, 0, 1 ),
        CheckListModel( Raid.ABRELSHOULD_1_2, 1490, null, 0, 0, 1 ),
        CheckListModel( Raid.ABRELSHOULD_3_4, 1500, null, 0, 0, 1 ),
        CheckListModel( Raid.ABRELSHOULD_5_6, 1520, null, 0, 0,1  ),
    ) )
    private fun filterList( level: Int ) {

        raid.value = raid.value?.filter { model ->

            level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 10000

        } as ArrayList<CheckListModel>

    }

    private fun setDailyList() {

        val raidList = pref.getRaidList()
        for ( index in 0 until raidList.size ) {
            Log.i("zxcv", "index :: ${index} :: ${raidList[index]}")

            raid.value!![index].checkedCount = raidList[index]

        }

    }

    fun setCharacterInfo( nickName: String, level: Int ) {

        this.nickName = nickName
        this.level = level
        pref.getPref( nickName )
        setDailyList()
        filterList ( level )

        for ( work in raid.value!! ) {

            Log.i("zxcv", "${work.work} checked :: ${work.checkedCount}")

        }

    }

    fun increaseCheckedCount( pos: Int, otherDifficulty: Int? ) {

//        Log.i("zxcv", "work:: $work")


        when ( raid.value!![pos].work ) {

            Raid.BALTAN_NORMAL -> {

                pref.bartanNormal = pref.bartanNormal + 1
                raid.value!![pos].checkedCount = pref.bartanNormal
                if ( pref.bartanHard == 1 ) {

                    pref.bartanHard = pref.bartanHard - 1
                    otherDifficulty?.let { raid.value!![it].checkedCount = pref.bartanHard }

                }

            }
            Raid.BALRAN_HARD -> {

                pref.bartanHard = pref.bartanHard + 1
                raid.value!![pos].checkedCount = pref.bartanHard
                if ( pref.bartanNormal == 1 ) {

                    pref.bartanNormal = pref.bartanNormal - 1
                    otherDifficulty?.let { raid.value!![it].checkedCount = pref.bartanNormal }

                }

            }
            Raid.VIAKISS_NORMAL -> {

                pref.viakissNormal = pref.viakissNormal + 1
                raid.value!![pos].checkedCount = pref.viakissNormal
                if ( pref.viakissHard == 1 ) {

                    pref.viakissHard = pref.viakissHard - 1
                    otherDifficulty?.let { raid.value!![it].checkedCount = pref.viakissHard }

                }

            }
            Raid.VIAKISS_HARD -> {

                pref.viakissHard = pref.viakissHard + 1
                raid.value!![pos].checkedCount = pref.viakissHard
                if ( pref.viakissNormal == 1 ) {

                    pref.viakissNormal = pref.viakissNormal - 1
                    otherDifficulty?.let { raid.value!![it].checkedCount = pref.viakissNormal }

                }

            }
            Raid.KOUKUSATON_REHEARSAL -> {

                pref.koukusatonRehearsal = pref.koukusatonRehearsal + 1
                raid.value!![pos].checkedCount = pref.koukusatonRehearsal

            }
            Raid.KOUKUSATON_NORMAL -> {

                pref.koutusatonNormal = pref.koutusatonNormal + 1
                raid.value!![pos].checkedCount = pref.koutusatonNormal

            }
            Raid.ABRELSHOULD_DEJAVU -> {

                pref.abrelshouldDevaju = pref.abrelshouldDevaju + 1
                raid.value!![pos].checkedCount = pref.abrelshouldDevaju

            }
            Raid.ABRELSHOULD_1_2 -> {

                pref.abrelshould12 = pref.abrelshould12 + 1
                raid.value!![pos].checkedCount = pref.abrelshould12

            }
            Raid.ABRELSHOULD_3_4 -> {

                pref.abrelshould34 = pref.abrelshould34 + 1
                raid.value!![pos].checkedCount = pref.abrelshould34

            }
            Raid.ABRELSHOULD_5_6 -> {

                pref.abrelshould56 = pref.abrelshould56 + 1
                raid.value!![pos].checkedCount = pref.abrelshould56

            }

        }

    }

    fun decreaseCheckedCount( pos: Int ) {

        when ( raid.value!![pos].work ) {

            Raid.BALTAN_NORMAL -> {

                pref.bartanNormal = pref.bartanNormal - 1
                raid.value!![pos].checkedCount = pref.bartanNormal

            }
            Raid.BALRAN_HARD -> {

                pref.bartanHard = pref.bartanHard - 1
                raid.value!![pos].checkedCount = pref.bartanHard

            }
            Raid.VIAKISS_NORMAL -> {

                pref.viakissNormal = pref.viakissNormal - 1
                raid.value!![pos].checkedCount = pref.viakissNormal

            }
            Raid.VIAKISS_HARD -> {

                pref.viakissHard = pref.viakissHard - 1
                raid.value!![pos].checkedCount = pref.viakissHard

            }
            Raid.KOUKUSATON_REHEARSAL -> {

                pref.koukusatonRehearsal = pref.koukusatonRehearsal - 1
                raid.value!![pos].checkedCount = pref.koukusatonRehearsal

            }
            Raid.KOUKUSATON_NORMAL -> {

                pref.koutusatonNormal = pref.koutusatonNormal - 1
                raid.value!![pos].checkedCount = pref.koutusatonNormal

            }
            Raid.ABRELSHOULD_DEJAVU -> {

                pref.abrelshouldDevaju = pref.abrelshouldDevaju - 1
                raid.value!![pos].checkedCount = pref.abrelshouldDevaju

            }
            Raid.ABRELSHOULD_1_2 -> {

                pref.abrelshould12 = pref.abrelshould12 - 1
                raid.value!![pos].checkedCount = pref.abrelshould12

            }
            Raid.ABRELSHOULD_3_4 -> {

                pref.abrelshould34 = pref.abrelshould34 - 1
                raid.value!![pos].checkedCount = pref.abrelshould34

            }
            Raid.ABRELSHOULD_5_6 -> {

                pref.abrelshould56 = pref.abrelshould56 - 1
                raid.value!![pos].checkedCount = pref.abrelshould56

            }

        }

    }

}