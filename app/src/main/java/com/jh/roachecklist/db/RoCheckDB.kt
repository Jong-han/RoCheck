package com.baycon.mobilefax.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jh.roachecklist.db.CharacterDAO
import com.jh.roachecklist.db.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1
)
abstract class RoCheckDB : RoomDatabase() {
    abstract fun characterDAO(): CharacterDAO
}