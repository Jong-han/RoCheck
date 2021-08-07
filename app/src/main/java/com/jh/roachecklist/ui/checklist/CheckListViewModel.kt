package com.jh.roachecklist.ui.checklist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.ui.character.CharacterActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor( savedState: SavedStateHandle ): BaseViewModel() {

    private val level: Int = savedState.get<Int>( CharacterActivity.EXTRA_LEVEL ) ?: 0
    val nickname = savedState.get<String>( CharacterActivity.EXTRA_NICK_NAME )

    var daily = arrayListOf<CheckListModel>(
        CheckListModel("길드 출석", 0, null, 0, 0, 1 ),
        CheckListModel("카오스 던전", 0, null, 0, 0, 2 ),
        CheckListModel("일일 에포나 의뢰", 0, null, 0, 0, 3),
        CheckListModel("호감도", 0, null, 0, 0, 1),
        CheckListModel("모험섬", 0, null, 0, 0, 1 ),
        CheckListModel("필드 보스", 0, null, 0, 0, 2 ),
        CheckListModel("가디언 토벌", 0, null, 0, 0, 2 ),
        CheckListModel("카오스 게이트", 0, null, 0, 0, 1 )
        )
    var weekly = arrayListOf<CheckListModel>(
        CheckListModel("도전 어비스 던전", 0, null, 0, 0, 2 ),
        CheckListModel("도전 가디언 토벌", 0, null, 0, 0, 3 ),
        CheckListModel("주간 에포나 의뢰", 0, null, 0, 0, 3 ),
        CheckListModel("아르고스 페이즈 1", 1370, null, 1500, 0, 1 ),
        CheckListModel("아르고스 페이즈 2", 1385, null, 800, 0, 1 ),
        CheckListModel("아르고스 페이즈 3", 1400, null, 1000, 0, 1 ),
        CheckListModel("유령선", 0, null, 0, 0, 1 ),
        CheckListModel("오레하의 우물 노멀", 0, null, 0, 0, 1 ),
        CheckListModel("오레하의 우물 하드", 0, null, 0, 0, 1 )
    )
    var raid = arrayListOf<CheckListModel>(
        CheckListModel("발탄 노멀", 1415, null, 3300, 0, 1 ),
        CheckListModel("발탄 하드", 1445, null, 4500, 0, 1 ),
        CheckListModel("발탄 헬", 1445, null, 0, 0, 1 ),
        CheckListModel("비아키스 노멀", 1430, null, 3300, 0, 1 ),
        CheckListModel("비아키스 하드", 1460, null, 4500, 0, 1 ),
        CheckListModel("비아키스 헬", 1460, null, 0, 0, 1 ),
        CheckListModel("쿠크세이튼 리허설", 1385, null, 0, 0, 1 ),
        CheckListModel("쿠크세이튼 노말", 1475, null, 0, 0, 1 ),
        CheckListModel("아브렐슈드 데자뷰", 1430, null, 0, 0, 1 ),
        CheckListModel("아브렐슈드 1/2 관문", 1490, null, 0, 0, 1 ),
        CheckListModel("아브렐슈드 3/4 관문", 1500, null, 0, 0, 1 ),
        CheckListModel("아브렐슈드 5/6 관문", 1520, null, 0, 0,1  ),
    )


    init {

         daily = daily.filter { model ->

             level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 9999

         } as ArrayList<CheckListModel>

        weekly = weekly.filter { model ->

            level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 9999

        } as ArrayList<CheckListModel>

        raid = raid.filter { model ->

            level >= model.minLevel ?: 0 &&  level < model.maxLevel ?: 9999

        } as ArrayList<CheckListModel>


    }

}