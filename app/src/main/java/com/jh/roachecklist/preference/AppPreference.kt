package com.jh.roachecklist.preference

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.jh.roachecklist.Const.DailyWork
import java.io.File


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AppPreference constructor(private val context: Context ) {

    private lateinit var sharedPreferences: SharedPreferences

    fun getPref( nickName: String = "expedition" ) {

        sharedPreferences = context.getSharedPreferences( nickName, 0 )

    }

    fun deletePref( nickName: String ) {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            context.deleteSharedPreferences( nickName )
//        } else {
            context.getSharedPreferences( nickName, 0 ).edit().clear().apply()
            val deletePrefFile =
                File( context.cacheDir.parent + "/shared_prefs/" + nickName + ".xml")
            deletePrefFile.delete()
//        }

    }

    /**
     * 일일 숙제 리스트
     */
    fun getDailyList(): ArrayList<Int> = arrayListOf( guild, dailyEfona, favorability, island, fieldBoss, dailyGuardian, chaosGate, chaosDungeon)

    fun getDailyMap(): HashMap<String,Int> {

        val result = HashMap<String, Int>()
        result[DailyWork.GUILD] = 0
        result[DailyWork.DAILY_EFONA] = 1
        result[DailyWork.FAVORABILITY] = 2
        result[DailyWork.ISLAND] = 3
        result[DailyWork.FIELD_BOSS] = 4
        result[DailyWork.DAILY_GUARDIAN] = 5
        result[DailyWork.CHAOS_GATE] = 6
        result[DailyWork.CHAOS_DUNGEON] = 7

        return result

    }

    var guild: Int
        get() = sharedPreferences.getInt("guild", 0)
        set(value) = sharedPreferences.edit().putInt("guild", value).apply()

    var chaosDungeon: Int
        get() = sharedPreferences.getInt("chaosDungeon", 0)
        set(value) = sharedPreferences.edit().putInt("chaosDungeon", value).apply()

    var dailyEfona: Int
        get() = sharedPreferences.getInt("dailyEfona", 0)
        set(value) = sharedPreferences.edit().putInt("dailyEfona", value).apply()

    var favorability: Int
        get() = sharedPreferences.getInt("favorability", 0)
        set(value) = sharedPreferences.edit().putInt("favorability", value).apply()

    var island: Int
        get() = sharedPreferences.getInt("island", 0)
        set(value) = sharedPreferences.edit().putInt("island", value).apply()

    var fieldBoss: Int
        get() = sharedPreferences.getInt("fieldBoss", 0)
        set(value) = sharedPreferences.edit().putInt("fieldBoss", value).apply()

    var dailyGuardian: Int
        get() = sharedPreferences.getInt("dailyGuardian", 0)
        set(value) = sharedPreferences.edit().putInt("dailyGuardian", value).apply()

    var chaosGate: Int
        get() = sharedPreferences.getInt("chaosGate", 0)
        set(value) = sharedPreferences.edit().putInt("chaosGate", value).apply()

    /**
     * 주간 숙제 리스트
     */

    fun getWeeklyList(): ArrayList<Int> = arrayListOf( challengeGuardian, weeklyEfona, argos1, argos2, argos3, ghostShip, orehaNomal, orehaHard, orehaBus )

    var challengeGuardian: Int
        get() = sharedPreferences.getInt("challengeGuardian", 0)
        set(value) = sharedPreferences.edit().putInt("challengeGuardian", value).apply()

    var weeklyEfona: Int
        get() = sharedPreferences.getInt("weeklyEfona", 0)
        set(value) = sharedPreferences.edit().putInt("weeklyEfona", value).apply()

    var argos1: Int
        get() = sharedPreferences.getInt("argos1", 0)
        set(value) = sharedPreferences.edit().putInt("argos1", value).apply()

    var argos2: Int
        get() = sharedPreferences.getInt("argos2", 0)
        set(value) = sharedPreferences.edit().putInt("argos2", value).apply()

    var argos3: Int
        get() = sharedPreferences.getInt("argos3", 0)
        set(value) = sharedPreferences.edit().putInt("argos3", value).apply()

    var ghostShip: Int
        get() = sharedPreferences.getInt("ghostShip", 0)
        set(value) = sharedPreferences.edit().putInt("ghostShip", value).apply()

    var orehaNomal: Int
        get() = sharedPreferences.getInt("orehaNomal", 0)
        set(value) = sharedPreferences.edit().putInt("orehaNomal", value).apply()

    var orehaHard: Int
        get() = sharedPreferences.getInt("orehaHard", 0)
        set(value) = sharedPreferences.edit().putInt("orehaHard", value).apply()

    var orehaBus: Int
        get() = sharedPreferences.getInt("orehaBus", 0)
        set(value) = sharedPreferences.edit().putInt("orehaBus", value).apply()

    /**
     * 군단장 레이드 리스트
     */
    fun getRaidList(): ArrayList<Int> = arrayListOf( bartanNormal, bartanHard, viakissNormal, viakissHard, koutusatonNormal, abrelshould12, abrelshould34, abrelshould56 )

    var bartanNormal: Int
        get() = sharedPreferences.getInt("bartanNormal", 0)
        set(value) = sharedPreferences.edit().putInt("bartanNormal", value).apply()
    var bartanHard: Int
        get() = sharedPreferences.getInt("bartanHard", 0)
        set(value) = sharedPreferences.edit().putInt("bartanHard", value).apply()
    var viakissNormal: Int
        get() = sharedPreferences.getInt("viakissNormal", 0)
        set(value) = sharedPreferences.edit().putInt("viakissNormal", value).apply()
    var viakissHard: Int
        get() = sharedPreferences.getInt("viakissHard", 0)
        set(value) = sharedPreferences.edit().putInt("viakissHard", value).apply()
    var koutusatonNormal: Int
        get() = sharedPreferences.getInt("koutusatonNormal", 0)
        set(value) = sharedPreferences.edit().putInt("koutusatonNormal", value).apply()
    var abrelshould12: Int
        get() = sharedPreferences.getInt("abrelshould12", 0)
        set(value) = sharedPreferences.edit().putInt("abrelshould12", value).apply()
    var abrelshould34: Int
        get() = sharedPreferences.getInt("abrelshould34", 0)
        set(value) = sharedPreferences.edit().putInt("abrelshould34", value).apply()
    var abrelshould56: Int
        get() = sharedPreferences.getInt("abrelshould56", 0)
        set(value) = sharedPreferences.edit().putInt("abrelshould56", value).apply()

    /**
     * 주간 원정대 숙제 리스트
     */
    fun getExpeditionList(): ArrayList<Int> = arrayListOf( challengeAbyssDungeon, koukusatonRehearsal, abrelshouldDevaju )

    var challengeAbyssDungeon: Int
        get() = sharedPreferences.getInt("challengeAbyssDungeon", 0)
        set(value) = sharedPreferences.edit().putInt("challengeAbyssDungeon", value).apply()

    var koukusatonRehearsal: Int
        get() = sharedPreferences.getInt("koukusatonRehearsal", 0)
        set(value) = sharedPreferences.edit().putInt("koukusatonRehearsal", value).apply()

    var abrelshouldDevaju: Int
        get() = sharedPreferences.getInt("abrelshouldDevaju", 0)
        set(value) = sharedPreferences.edit().putInt("abrelshouldDevaju", value).apply()


}