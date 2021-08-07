package com.jh.roachecklist.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CharacterDAO {
    @Insert
    fun insert(entity: CharacterEntity)

    @Query("SELECT * FROM Character")
    fun getAll(): List<CharacterEntity>

    @Query("DELETE FROM Character")
    fun clearTable()

    @Update
    fun update(entity: CharacterEntity)

//    @Query("SELECT user_Name FROM Contact WHERE user_Number = :number")
//    fun searchName(number: String) : String

    @Delete
    fun deleteCharacter(entity: CharacterEntity)
}
