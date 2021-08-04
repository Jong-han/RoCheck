package com.jh.roachecklist.ui.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(): BaseViewModel() {

    private val _rvItem = MutableLiveData<ArrayList<CharacterModel>>()
    val rvItem: LiveData<ArrayList<CharacterModel>>
        get() = _rvItem

    val clickAddCharacter = SingleLiveEvent<Any>()
    fun clickAddCharacter() {
        clickAddCharacter.call()
    }

    fun createCharacter( name: String, level: Int, klass: String ) {

        val character = CharacterModel( name, level, klass )
        val characterList: ArrayList<CharacterModel> = _rvItem.value ?: ArrayList()
        characterList.add( character )
        _rvItem.value = characterList

    }

}