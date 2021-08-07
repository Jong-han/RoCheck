package com.jh.roachecklist.ui.character

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jh.roachecklist.db.CharacterEntity
import com.jh.roachecklist.repository.Repository
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.utils.ListLiveData
import com.jh.roachecklist.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor( private val repository: Repository ): BaseViewModel() {

    val rvItems = ListLiveData<CharacterEntity>()

    init {

        viewModelScope.launch( Dispatchers.IO ) {

            val temp = repository.getAllCharacters()
            withContext( Dispatchers.Main ) {
//
                rvItems.addAll( temp )

            }

        }

    }

    val clickAddCharacter = SingleLiveEvent<Any>()
    fun clickAddCharacter() {
        clickAddCharacter.call()
    }

    fun addCharacter(name: String, level: Int, klass: String ) {

        val character = CharacterEntity( name, klass, level )

        viewModelScope.launch( Dispatchers.IO ) {

            repository.addCharacter( character )

        }
        rvItems.add( character )

    }
    fun updateCharacter( character: CharacterEntity, level: Int ) {

        viewModelScope.launch( Dispatchers.IO ) {

            character.level = level
            repository.updateCharacter( character )
            withContext( Dispatchers.Main ) {

                rvItems.get( rvItems.indexOf( character ) ).level = level

            }

        }


    }

    fun deleteCharacter( character: CharacterEntity ) {

        viewModelScope.launch( Dispatchers.IO ) {

            repository.deleteCharacter( character )

        }

    }

}