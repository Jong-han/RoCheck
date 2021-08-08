package com.jh.roachecklist.ui.character

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jh.roachecklist.db.CharacterEntity
import com.jh.roachecklist.repository.Repository
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.utils.ListLiveData
import com.jh.roachecklist.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor( private val repository: Repository ): BaseViewModel() {

    enum class CharacterEvent { EXIST }

    val rvItems = ListLiveData<CharacterEntity>()

    val event = MutableLiveData<CharacterEvent>()

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

    fun addCharacter(nickName: String, level: Int, klass: String ) {

        val character = CharacterEntity(nickName, klass, level)

        viewModelScope.launch(Dispatchers.IO) {

            if ( repository.isExist( nickName ) ) {

                withContext( Dispatchers.Main ) {

                    event.value = CharacterEvent.EXIST

                }

            } else {

                Log.i("zxcvzxcv", " 캐릭터생성")
                repository.addCharacter(character)
                withContext(Dispatchers.Main) {

                    rvItems.add(character)

                }

            }

        }

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
        rvItems.remove( character )

    }

}