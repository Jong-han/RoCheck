package com.jh.roachecklist.ui.checklist.expedition

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.repository.Repository
import com.jh.roachecklist.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpeditionViewModel @Inject constructor( private val repository: Repository,
                                               private val pref: AppPreference,
                                               savedState: SavedStateHandle): BaseViewModel() {

    var level = 0

    fun setCharacterInfo( ) {

        pref.getPref()

        viewModelScope.launch( Dispatchers.IO ) {

            level = repository.getHighestLevel() ?: 0

        }

    }

}