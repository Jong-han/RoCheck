package com.jh.roachecklist.ui.character

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jh.roachecklist.db.CharacterEntity
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.repository.Repository
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.utils.CheckListUtil
import com.jh.roachecklist.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor( private val repository: Repository, private val pref: AppPreference, private val checkListUtil: CheckListUtil ): BaseViewModel() {

    enum class CharacterEvent { EXIST }

    val rvItems = MutableLiveData<List<CharacterEntity>>()

    val originalList = repository.getAllCharacters()

    val event = MutableLiveData<CharacterEvent>()

    val clickExpedition = SingleLiveEvent<Any>()
    fun clickExpedition() {

        viewModelScope.launch( Dispatchers.IO) {

            withContext( Dispatchers.Main ) {

                clickExpedition.call()

            }

        }

    }

    val clickReset = SingleLiveEvent<Any>()
    fun clickReset() {

        clickReset.call()

    }

    val clickAddCharacter = SingleLiveEvent<Any>()
    fun clickAddCharacter() {
        clickAddCharacter.call()
    }

    val clickSetting = SingleLiveEvent<Any>()
    fun clickSetting() {

        clickSetting.call()

    }

    fun addCharacter(nickName: String, level: Int, klass: String, favorite: Int ) {

        val character = CharacterEntity( nickName, klass, level, favorite )

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

        Log.i("zxcvzxcv","setRvItems start ${System.currentTimeMillis()}")
        rvItems.value = items.map {

            it.apply {

                runBlocking {

                    launch(Dispatchers.IO) {

                        dailySuccess = !checkListUtil.alarmDaily( listOf( it ) )
                        weeklySuccess = ! ( checkListUtil.alarmWeekly( listOf( it ) ) || checkListUtil.alarmRaid( listOf( it ) ) )

                    }

                }

            }

        }
        Log.i("zxcvzxcv","setRvItems end ${System.currentTimeMillis()}")


    }

    fun editFavoriteType( characterEntity: CharacterEntity, favorite: Int ) {

        viewModelScope.launch {

            characterEntity.favorite = favorite
            withContext( Dispatchers.IO ) {

                repository.updateCharacter( characterEntity )

            }

        }

    }

    fun saveTime( hour: Int, minute: Int ) {

        pref.getPref()
        pref.hour = hour
        pref.minute = minute

    }

    fun updateSuccessState( pos: Int ) {

        rvItems.value!![pos].run {

            runBlocking {

                launch(Dispatchers.IO) {

                    dailySuccess = !checkListUtil.alarmDaily( listOf( this@run ) )
                    weeklySuccess = ! ( checkListUtil.alarmWeekly( listOf( this@run ) ) || checkListUtil.alarmRaid( listOf( this@run ) ) )

                }

            }

        }
        rvItems.value = rvItems.value

    }

}
