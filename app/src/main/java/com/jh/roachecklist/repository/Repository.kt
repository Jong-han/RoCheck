package com.jh.roachecklist.repository

import androidx.lifecycle.LiveData
import com.baycon.mobilefax.db.RoCheckDB
import com.jh.roachecklist.db.CharacterEntity

class Repository( private val db: RoCheckDB ) {

//    fun getAllCharacter(): LiveData<List<CharacterEntity>> {
//
//        return db.characterDAO().getAll()
//
//    }

    fun getAllCharacters(): List<CharacterEntity> {

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

    fun deleteAllCharacter() {

        db.characterDAO().clearTable()

    }

}