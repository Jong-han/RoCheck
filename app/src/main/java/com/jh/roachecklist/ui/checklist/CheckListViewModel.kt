package com.jh.roachecklist.ui.checklist

import androidx.lifecycle.SavedStateHandle
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.ui.character.CharacterActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor( savedState: SavedStateHandle ): BaseViewModel() {

    val level: Int = savedState.get<Int>( CharacterActivity.EXTRA_LEVEL ) ?: 0
    val nickname = savedState.get<String>( CharacterActivity.EXTRA_NICK_NAME )

    var daily = ArrayList<CheckListModel>()
    var weekly = ArrayList<CheckListModel>()
    var raid = ArrayList<CheckListModel>()


    init {

        for ( i in 1..10 ) {

            daily.add( CheckListModel("$i 번째 ", i, 0, 0, 0) )
            daily = daily.filter { model ->

                level >= model.minLevel ?: 0

            } as ArrayList<CheckListModel>

        }

    }

}