package com.jh.roachecklist.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.baycon.mobilefax.db.RoCheckDB
import com.jh.roachecklist.db.CharacterEntity
import com.jh.roachecklist.preference.AppPreference

class Repository( private val db: RoCheckDB ) {

//    fun getAllCharacter(): LiveData<List<CharacterEntity>> {
//
//        return db.characterDAO().getAll()
//
//    }

    fun getAllCharacters(): LiveData<List<CharacterEntity>> {

        return db.characterDAO().getAll()

    }

    fun addCharacter( characterEntity: CharacterEntity ) {

        db.characterDAO().insert( characterEntity )

    }

    fun updateCharacter( characterEntity: CharacterEntity ) {

        db.characterDAO().update( characterEntity )

    }

    fun deleteCharacter( characterEntity: CharacterEntity ) {

        db.characterDAO().deleteCharacter( characterEntity )

    }

    fun isExist( character: CharacterEntity ): Boolean {

        return db.characterDAO().searchCharacter( character.nickName ) != null

    }

    fun deleteAllCharacter() {

        db.characterDAO().clearTable()

    }

    suspend fun getHighestLevel(): Int? = db.characterDAO().getHighestLevel()

}