package com.jh.roachecklist.repository

import androidx.lifecycle.LiveData
import com.baycon.mobilefax.db.RoCheckDB
import com.jh.roachecklist.Const
import com.jh.roachecklist.db.CharacterEntity
import com.jh.roachecklist.db.CheckListEntity

class Repository( private val db: RoCheckDB ) {

//    fun getAllCharacter(): LiveData<List<CharacterEntity>> {
//
//        return db.characterDAO().getAll()
//
//    }

    fun getAllCharacters(): LiveData<List<CharacterEntity>> {

        return db.characterDAO().getAll()

    }

    fun getAllCharacterList(): List<CharacterEntity> {

        return db.characterDAO().getAllList()

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

    fun getHighestLevel(): Int? = db.characterDAO().getHighestLevel()

    fun getNickNameList(): List<String> = db.characterDAO().getCharacterNickNameList()

    fun insertCheckList() {

        db.checkListDAO().run {

            insert(CheckListEntity( Const.DailyWork.GUILD, 0, Const.MAX_LEVEL, 0, 0, 1, Const.WorkType.DAILY ))
            insert(CheckListEntity( Const.DailyWork.DAILY_EFONA, 0,  Const.MAX_LEVEL, 0, 0, 3, Const.WorkType.DAILY ))
            insert(CheckListEntity( Const.DailyWork.FAVORABILITY, 0,  Const.MAX_LEVEL, 0, 0, 1, Const.WorkType.DAILY ))
            insert(CheckListEntity( Const.DailyWork.ISLAND, 0,  Const.MAX_LEVEL, 0, 0, 1, Const.WorkType.DAILY ))
            insert(CheckListEntity( Const.DailyWork.FIELD_BOSS, 0,  Const.MAX_LEVEL, 0, 0, 1, Const.WorkType.DAILY ))
            insert(CheckListEntity( Const.DailyWork.DAILY_GUARDIAN, 0,  Const.MAX_LEVEL, 0, 0, 2, Const.WorkType.DAILY ))
            insert(CheckListEntity( Const.DailyWork.CHAOS_GATE, 0,  Const.MAX_LEVEL, 0, 0, 1, Const.WorkType.DAILY ))
            insert(CheckListEntity( Const.DailyWork.CHAOS_DUNGEON, 0,  Const.MAX_LEVEL, 0, 0, 2, Const.WorkType.DAILY ))

            insert(CheckListEntity( Const.Expedition.CHALLENGE_ABYSS_DUNGEON, 0,  Const.MAX_LEVEL, 0, 0, 1, Const.WorkType.EXPEDITION ))
            insert(CheckListEntity( Const.Expedition.KOUKUSATON_REHEARSAL, 1385,  Const.MAX_LEVEL, 0, 0, 1, Const.WorkType.EXPEDITION ))
            insert(CheckListEntity( Const.Expedition.ABRELSHOULD_DEJAVU, 1430,  Const.MAX_LEVEL, 0, 0, 1, Const.WorkType.EXPEDITION ))

            insert(CheckListEntity( Const.Raid.BALTAN, 1415,  Const.MAX_LEVEL, 3300, 0, 1, Const.WorkType.RAID ))
            insert(CheckListEntity( Const.Raid.VIAKISS, 1430,  Const.MAX_LEVEL, 3300, 0, 1, Const.WorkType.RAID ))
            insert(CheckListEntity( Const.Raid.KOUKUSATON, 1475,  Const.MAX_LEVEL, 4500, 0, 1, Const.WorkType.RAID ))
            insert(CheckListEntity( Const.Raid.ABRELSHOULD_1_2, 1490,  Const.MAX_LEVEL, 4500, 0, 1, Const.WorkType.RAID ))
            insert(CheckListEntity( Const.Raid.ABRELSHOULD_3_4, 1500,  Const.MAX_LEVEL, 1500, 0, 1, Const.WorkType.RAID ))
            insert(CheckListEntity( Const.Raid.ABRELSHOULD_5_6, 1520,  Const.MAX_LEVEL, 1500, 0,1, Const.WorkType.RAID ))

            insert(CheckListEntity( Const.WeeklyWork.CHALLENGE_GUARDIAN, 0,  Const.MAX_LEVEL, 0, 0, 3, Const.WorkType.WEEKLY ))
            insert(CheckListEntity( Const.WeeklyWork.WEEKLY_EFONA, 0,  Const.MAX_LEVEL, 0, 0, 3, Const.WorkType.WEEKLY ))
            insert(CheckListEntity( Const.WeeklyWork.ARGOS_1, 1370, 1475, 1500, 0, 1, Const.WorkType.WEEKLY ))
            insert(CheckListEntity( Const.WeeklyWork.ARGOS_2, 1385, 1475, 800, 0, 1, Const.WorkType.WEEKLY ))
            insert(CheckListEntity( Const.WeeklyWork.ARGOS_3, 1400, 1475, 1000, 0, 1, Const.WorkType.WEEKLY ))
            insert(CheckListEntity( Const.WeeklyWork.GHOST_SHIP, 0,  Const.MAX_LEVEL, 0, 0, 1, Const.WorkType.WEEKLY ))
            insert(CheckListEntity( Const.WeeklyWork.OREHA, 1325, 1415, 1500, 0, 1, Const.WorkType.WEEKLY ))
        }

    }

    fun getDailyCheckList(): List<CheckListEntity> = db.checkListDAO().getAllTypedList(Const.WorkType.DAILY)
    fun getDailyFilteredCheckList( level: Int ): List<CheckListEntity> = db.checkListDAO().getFilteredList(Const.WorkType.DAILY, level)
    fun getDailyCantCheckList( level: Int ): List<CheckListEntity> = db.checkListDAO().getCantList( Const.WorkType.DAILY, level )

    fun getWeeklyCheckList(): List<CheckListEntity> = db.checkListDAO().getAllTypedList(Const.WorkType.WEEKLY)
    fun getWeeklyFilteredCheckList( level: Int ): List<CheckListEntity> = db.checkListDAO().getFilteredList(Const.WorkType.WEEKLY, level)
    fun getWeeklyCantCheckList( level: Int ): List<CheckListEntity> = db.checkListDAO().getCantList( Const.WorkType.WEEKLY, level )

    fun getExpeditionCheckList(): List<CheckListEntity> = db.checkListDAO().getAllTypedList(Const.WorkType.EXPEDITION)
    fun getExpeditionFilteredCheckList( level: Int ): List<CheckListEntity> = db.checkListDAO().getFilteredList(Const.WorkType.EXPEDITION, level)
    fun getExpeditionCantCheckList( level: Int ): List<CheckListEntity> = db.checkListDAO().getCantList( Const.WorkType.EXPEDITION, level )

    fun getRaidCheckList(): List<CheckListEntity> = db.checkListDAO().getAllTypedList(Const.WorkType.RAID)
    fun getRaidFilteredCheckList( level: Int ): List<CheckListEntity> = db.checkListDAO().getFilteredList(Const.WorkType.RAID, level)
    fun getRaidCantCheckList( level: Int ): List<CheckListEntity> = db.checkListDAO().getCantList( Const.WorkType.RAID, level )



}