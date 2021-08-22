package com.baycon.mobilefax.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jh.roachecklist.db.CharacterDAO
import com.jh.roachecklist.db.CharacterEntity
import com.jh.roachecklist.db.CheckListDAO
import com.jh.roachecklist.db.CheckListEntity

@Database(
    entities = [CharacterEntity::class, CheckListEntity::class],
    version = 1
)
abstract class RoCheckDB : RoomDatabase() {
    abstract fun characterDAO(): CharacterDAO
    abstract fun checkListDAO(): CheckListDAO
}