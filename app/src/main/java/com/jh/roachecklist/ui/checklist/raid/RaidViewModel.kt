package com.jh.roachecklist.ui.checklist.raid

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jh.roachecklist.Const
import com.jh.roachecklist.Const.Raid
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
class RaidViewModel @Inject constructor( private val pref: AppPreference,
                                         private val repository: Repository ): BaseViewModel() {

    private var nickName = ""
    private var level = 0

    var raid = MutableLiveData<List<CheckListEntity>>()

    private fun filterList( level: Int ) {

        raid.value = raid.value?.filter { model ->

            level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 10000

        } as ArrayList<CheckListEntity>

    }

    private fun setRaidList() {

        val raidList = pref.getRaidList()
        val raidNotiList = pref.getRaidNotiList()
        for ( index in 0 until raidList.size ) {

            raid.value!![index].checkedCount = raidList[index]
            raid.value!![index].isNoti = raidNotiList[index]

        }

    }

    fun setCharacterInfo( nickName: String, level: Int ) {

        this.nickName = nickName
        this.level = level
        pref.getPref( nickName )

        viewModelScope.launch( Dispatchers.IO ) {

            val result =  repository.getRaidCheckList()
            withContext( Dispatchers.Main ) {

                raid.value = result
                setRaidList()
                filterList ( level )

            }

        }

    }

    fun increaseCheckedCount( pos: Int, otherDifficulty: Int? ) {

//        Log.i("zxcv", "work:: $work")


        when ( raid.value!![pos].work ) {

            Raid.BALTAN -> {

                pref.bartan = pref.bartan + 1
                raid.value!![pos].checkedCount = pref.bartan

            }
            Raid.VIAKISS -> {

                pref.viakiss = pref.viakiss + 1
                raid.value!![pos].checkedCount = pref.viakiss

            }
            Raid.KOUKUSATON -> {

                pref.koutusaton = pref.koutusaton + 1
                raid.value!![pos].checkedCount = pref.koutusaton

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

            Raid.BALTAN -> {

                pref.bartan = pref.bartan - 1
                raid.value!![pos].checkedCount = pref.bartan

            }
            Raid.VIAKISS -> {

                pref.viakiss = pref.viakiss - 1
                raid.value!![pos].checkedCount = pref.viakiss

            }
            Raid.KOUKUSATON -> {

                pref.koutusaton = pref.koutusaton - 1
                raid.value!![pos].checkedCount = pref.koutusaton

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

    fun onClickNoti( pos: Int ) {
        Log.i("zxcv","눌린거 :: ${raid.value!![pos].work}")

        when ( raid.value!![pos].work ) {

            Const.Raid.BALTAN -> {

                if ( pref.bartanNoti >= Const.NotiState.YES )
                    pref.bartanNoti = Const.NotiState.NO
                else
                    pref.bartanNoti = Const.NotiState.YES

                raid.value!![pos].isNoti = pref.bartanNoti

            }
            Const.Raid.VIAKISS -> {

                if ( pref.viakissNoti >= Const.NotiState.YES )
                    pref.viakissNoti = Const.NotiState.NO
                else
                    pref.viakissNoti = Const.NotiState.YES
                raid.value!![pos].isNoti = pref.viakissNoti

            }
            Const.Raid.KOUKUSATON -> {

                if ( pref.koutosatonNoti >= Const.NotiState.YES )
                    pref.koutosatonNoti = Const.NotiState.NO
                else
                    pref.koutosatonNoti = Const.NotiState.YES
                raid.value!![pos].isNoti = pref.koutosatonNoti

            }
            Const.Raid.ABRELSHOULD_1_2 -> {

                if ( pref.abrelshould12Noti >= Const.NotiState.YES )
                    pref.abrelshould12Noti = Const.NotiState.NO
                else
                    pref.abrelshould12Noti = Const.NotiState.YES
                raid.value!![pos].isNoti = pref.abrelshould12Noti

            }
            Const.Raid.ABRELSHOULD_3_4 -> {

                if ( pref.abrelshould34Noti >= Const.NotiState.YES )
                    pref.abrelshould34Noti = Const.NotiState.NO
                else
                    pref.abrelshould34Noti = Const.NotiState.YES
                raid.value!![pos].isNoti = pref.abrelshould34Noti

            }
            Const.Raid.ABRELSHOULD_5_6 -> {

                if ( pref.abrelshould56Noti >= Const.NotiState.YES )
                    pref.abrelshould56Noti = Const.NotiState.NO
                else
                    pref.abrelshould56Noti = Const.NotiState.YES
                raid.value!![pos].isNoti = pref.abrelshould56Noti

            }

        }

    }

}