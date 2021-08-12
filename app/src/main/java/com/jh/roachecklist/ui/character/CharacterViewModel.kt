package com.jh.roachecklist.ui.character

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jh.roachecklist.db.CharacterEntity
import com.jh.roachecklist.preference.AppPreference
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
class CharacterViewModel @Inject constructor( private val repository: Repository, private val pref: AppPreference ): BaseViewModel() {

    enum class CharacterEvent { EXIST }

    val rvItems = MutableLiveData<List<CharacterEntity>>()

    val originalList = repository.getAllCharacters()

    val event = MutableLiveData<CharacterEvent>()

    var level = 0

    init {

        viewModelScope.launch( Dispatchers.IO) {

            setHighestLevel()

        }

    }

    val clickExpedition = SingleLiveEvent<Any>()
    fun clickExpedition() {

        viewModelScope.launch( Dispatchers.IO) {

            setHighestLevel()
            withContext( Dispatchers.Main ) {

                clickExpedition.call()

            }

        }

    }

    val clickAddCharacter = SingleLiveEvent<Any>()
    fun clickAddCharacter() {
        clickAddCharacter.call()
    }

    val clickSetting = SingleLiveEvent<Any>()
    fun clickSetting() {

        clickSetting.call()

    }

    fun addCharacter(nickName: String, level: Int, klass: String ) {

        val character = CharacterEntity(nickName, klass, level)

        viewModelScope.launch(Dispatchers.IO) {
            val isExist = repository.isExist( character )
            if ( isExist ) {
                Log.i("zxcvzxcv","캐릭터가 존재합니다.")
                withContext( Dispatchers.Main ) {

                    event.value = CharacterEvent.EXIST

                }

            } else {

                Log.i("zxcvzxcv", " 캐릭터생성")
                repository.addCharacter(character)

            }

        }

    }
    fun updateCharacter( character: CharacterEntity, level: Int ) {

        viewModelScope.launch( Dispatchers.IO ) {

            character.level = level
            repository.updateCharacter( character )

        }


    }

    fun deleteCharacter( character: CharacterEntity ) {

        viewModelScope.launch( Dispatchers.IO ) {

            repository.deleteCharacter( character )
            withContext( Dispatchers.Main ) {

                pref.deletePref( character.nickName )

            }

        }

    }

    fun setRvItems(items: List<CharacterEntity>) {

        rvItems.value = items

    }

    private fun setHighestLevel() {

        viewModelScope.launch ( Dispatchers.IO ) {

            level = repository.getHighestLevel() ?: 0

        }

    }

    fun getHighestLevel() = level

}