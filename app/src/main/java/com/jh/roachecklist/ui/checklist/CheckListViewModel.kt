package com.jh.roachecklist.ui.checklist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.ui.character.CharacterActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor( savedState: SavedStateHandle ): BaseViewModel() {

//    val level: Int = savedState.get<Int>( CharacterActivity.EXTRA_LEVEL ) ?: 0
    val nickname: String = " (${savedState.get<String>( CharacterActivity.EXTRA_NICK_NAME ) ?: ""})"

    val activeFragment = MutableLiveData<Int>()
    fun setActiveFragment( active: Int ) {

        activeFragment.value = active

    }

}