package com.jh.roachecklist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Character")
data class CharacterEntity(
    @ColumnInfo(name = "nick_name") val nickName: String,
    @ColumnInfo(name = "klass") val klass: String,
    @ColumnInfo(name = "level") var level: Int,
    @ColumnInfo(name = "favorite") var favorite: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Ignore var dailySuccess = false
    @Ignore var weeklySuccess = false
}
