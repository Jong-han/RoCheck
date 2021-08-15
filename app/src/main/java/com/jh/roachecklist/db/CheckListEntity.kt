package com.jh.roachecklist.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CheckList")
data class CheckListEntity(
    val work: String,
    val minLevel: Int?,
    val maxLevel: Int?,
    val gold: Int?,
    val state: Int,
    val count: Int,
    var type: String,
    var checkedCount: Int = 0,
    var isNoti: Int = 1
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
