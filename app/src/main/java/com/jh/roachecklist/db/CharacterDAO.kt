package com.jh.roachecklist.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CharacterDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: CharacterEntity)

    @Query("SELECT * FROM Character ORDER BY favorite DESC")
    fun getAll(): LiveData<List<CharacterEntity>>

    @Query("SELECT * FROM Character ORDER BY favorite DESC")
    fun getAllList(): List<CharacterEntity>

    @Query("DELETE FROM Character")
    fun clearTable()

    @Update
    fun update(entity: CharacterEntity)

//    @Query("SELECT user_Name FROM Contact WHERE user_Number = :number")
//    fun searchName(number: String) : String

    @Delete
    fun deleteCharacter(entity: CharacterEntity)

    @Query("SELECT * FROM Character WHERE nick_name = :nickName ")
    fun searchCharacter( nickName: String ): CharacterEntity?

    @Query("SELECT level FROM Character ORDER BY level DESC LIMIT 1")
    fun getHighestLevel(): Int?

    @Query("SELECT nick_name FROM Character")
    fun getCharacterNickNameList(): List<String>

}
