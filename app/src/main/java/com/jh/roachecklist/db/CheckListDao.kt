package com.jh.roachecklist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CheckListDao {

    @Query("SELECT * FROM CheckList")
    fun getAll(): List<CheckListEntity>

    @Query("SELECT * FROM CheckList WHERE type = :type")
    fun getAllTypedList( type: String ): List<CheckListEntity>

    @Insert
    fun insert( checkListEntity: CheckListEntity)

    @Query("SELECT * FROM CheckList WHERE type = :type and :level >= minLevel and :level < maxLevel")
    fun getFilteredList( type: String, level: Int ): List<CheckListEntity>

    @Query("SELECT * FROM CheckList WHERE type = :type and ( :level < minLevel or :level >= maxLevel )")
    fun getCantList( type: String, level: Int ): List<CheckListEntity>

}