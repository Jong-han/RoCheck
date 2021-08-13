package com.jh.roachecklist.preference

import android.content.Context
import android.content.SharedPreferences
import com.jh.roachecklist.Const
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

    var isFirst: Boolean
        get() = sharedPreferences.getBoolean("isFirst", true)
        set(value) = sharedPreferences.edit().putBoolean("isFirst", value).apply()

    /**
     * 일일 숙제 리스트
     */
    fun getDailyList(): ArrayList<Int> = arrayListOf( guild, dailyEfona, favorability, island, fieldBoss, dailyGuardian, chaosGate, chaosDungeon)

    fun resetDaily() {

        guild = 0
        chaosDungeon = 0
        dailyEfona = 0
        favorability = 0
        island = 0
        fieldBoss = 0
        dailyGuardian = 0
        chaosDungeon = 0
        chaosGate = 0

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
     * 휴식 보너스
     */
    fun getRestList(): ArrayList<Int> = arrayListOf( efonaRestBonus, chaosRestBonus, guardianRestBonus )

    var efonaRestBonus: Int
        get() {

            return if ( sharedPreferences.getInt("efonaRestBonus", 0) <= 0 )
                0
            else
                sharedPreferences.getInt("efonaRestBonus", 0)

        }
        set(value) {

            when {
                value <= 0 -> sharedPreferences.edit().putInt("efonaRestBonus", 0).apply()
                value >= 100 -> sharedPreferences.edit().putInt("efonaRestBonus", 100).apply()
                else -> sharedPreferences.edit().putInt("efonaRestBonus", value).apply()
            }

        }

    fun consumeEfonaRestBonus( count: Int ) {

        var tryCount = count

        while ( tryCount > 0 ) {

            if ( efonaRestBonus >= 20 ) {

                efonaRestBonus -= Const.Rest.CONSUME_REST_BONUS
                tryCount--

            }
            else
                break

        }

    }

    var chaosRestBonus: Int
        get() {

            return if ( sharedPreferences.getInt("chaosRestBonus", 0) <= 0 )
                0
            else
                sharedPreferences.getInt("chaosRestBonus", 0)

        }
        set(value) {

            when {
                value <= 0 -> sharedPreferences.edit().putInt("chaosRestBonus", 0).apply()
                value >= 100 -> sharedPreferences.edit().putInt("chaosRestBonus", 100).apply()
                else -> sharedPreferences.edit().putInt("chaosRestBonus", value).apply()
            }

        }

    fun consumeChaosRestBonus( count: Int ) {

        var tryCount = count

        while ( tryCount > 0 ) {

            if ( chaosRestBonus >= 20 ) {

                chaosRestBonus -= Const.Rest.CONSUME_REST_BONUS
                tryCount--

            }
            else
                break

        }

    }

    var guardianRestBonus: Int
        get() {

            return if ( sharedPreferences.getInt("guardianRestBonus", 0) <= 0 )
                0
            else
                sharedPreferences.getInt("guardianRestBonus", 0)

        }
        set(value) {

            when {
                value <= 0 -> sharedPreferences.edit().putInt("guardianRestBonus", 0).apply()
                value >= 100 -> sharedPreferences.edit().putInt("guardianRestBonus", 100).apply()
                else -> sharedPreferences.edit().putInt("guardianRestBonus", value).apply()
            }

        }

    fun consumeGuardianRestBonus( count: Int ) {

        var tryCount = count

        while ( tryCount > 0 ) {

            if ( guardianRestBonus >= 20 ) {

                guardianRestBonus -= Const.Rest.CONSUME_REST_BONUS
                tryCount--

            }
            else
                break

        }

    }



    /**
     * 주간 숙제 리스트
     */

    fun getWeeklyList(): ArrayList<Int> = arrayListOf( challengeGuardian, weeklyEfona, argos1, argos2, argos3, ghostShip, oreha )

    fun resetWeekly() {

        challengeGuardian = 0
        weeklyEfona = 0
        argos1 = 0
        argos2 = 0
        argos3 = 0
        ghostShip = 0
        oreha = 0

        bartan = 0
        viakiss = 0
        koutusaton = 0
        abrelshould12 = 0
        abrelshould34 = 0
        abrelshould56 = 0

    }

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

    var oreha: Int
        get() = sharedPreferences.getInt("oreha", 0)
        set(value) = sharedPreferences.edit().putInt("oreha", value).apply()

    /**
     * 군단장 레이드 리스트
     */
    fun getRaidList(): ArrayList<Int> = arrayListOf( bartan, viakiss, koutusaton, abrelshould12, abrelshould34, abrelshould56 )

    var bartan: Int
        get() = sharedPreferences.getInt("bartanNormal", 0)
        set(value) = sharedPreferences.edit().putInt("bartanNormal", value).apply()
    var viakiss: Int
        get() = sharedPreferences.getInt("viakissNormal", 0)
        set(value) = sharedPreferences.edit().putInt("viakissNormal", value).apply()
    var koutusaton: Int
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

    fun resetExpedition() {

        getPref()
        challengeAbyssDungeon = 0
        koukusatonRehearsal = 0
        abrelshouldDevaju = 0

    }

    var challengeAbyssDungeon: Int
        get() = sharedPreferences.getInt("challengeAbyssDungeon", 0)
        set(value) = sharedPreferences.edit().putInt("challengeAbyssDungeon", value).apply()

    var koukusatonRehearsal: Int
        get() = sharedPreferences.getInt("koukusatonRehearsal", 0)
        set(value) = sharedPreferences.edit().putInt("koukusatonRehearsal", value).apply()

    var abrelshouldDevaju: Int
        get() = sharedPreferences.getInt("abrelshouldDevaju", 0)
        set(value) = sharedPreferences.edit().putInt("abrelshouldDevaju", value).apply()


    /**
     * 각 리스트당 노티 여부
     */
    fun getDailyNotiList(): ArrayList<Int> = arrayListOf( guildNoti, dailyEfonaNoti, favorabilityNoti, islandNoti, fieldBossNoti, dailyGuardianNoti, chaosGateNoti, chaosDungeonNoti )

    var guildNoti: Int
        get() = sharedPreferences.getInt("guildNoti", 1)
        set(value) = sharedPreferences.edit().putInt("guildNoti", value).apply()
    var dailyEfonaNoti: Int
        get() = sharedPreferences.getInt("dailyEfonaNoti", 1) * 3
        set(value) = sharedPreferences.edit().putInt("dailyEfonaNoti", value).apply()
    var favorabilityNoti: Int
        get() = sharedPreferences.getInt("favorabilityNoti", 1)
        set(value) = sharedPreferences.edit().putInt("favorabilityNoti", value).apply()
    var islandNoti: Int
        get() = sharedPreferences.getInt("islandNoti", 1)
        set(value) = sharedPreferences.edit().putInt("islandNoti", value).apply()
    var fieldBossNoti: Int
        get() = sharedPreferences.getInt("fieldBossNoti", 1)
        set(value) = sharedPreferences.edit().putInt("fieldBossNoti", value).apply()
    var dailyGuardianNoti: Int
        get() = sharedPreferences.getInt("dailyGuardianNoti", 1) * 2
        set(value) = sharedPreferences.edit().putInt("dailyGuardianNoti", value).apply()
    var chaosGateNoti: Int
        get() = sharedPreferences.getInt("chaosGateNoti", 1)
        set(value) = sharedPreferences.edit().putInt("chaosGateNoti", value).apply()
    var chaosDungeonNoti: Int
        get() = sharedPreferences.getInt("chaosDungeonNoti", 1) * 2
        set(value) = sharedPreferences.edit().putInt("chaosDungeonNoti", value).apply()

    fun getWeeklyNotiList(): ArrayList<Int> = arrayListOf( challengeGuardianNoti, weeklyEfonaNoti, argos1Noti, argos2Noti, argos3Noti, ghostShipNoti, orehaNoti )

    var challengeGuardianNoti: Int
        get() = sharedPreferences.getInt("challengeGuardianNoti", 1) * 3
        set(value) = sharedPreferences.edit().putInt("challengeGuardianNoti", value).apply()
    var weeklyEfonaNoti: Int
        get() = sharedPreferences.getInt("weeklyEfonaNoti", 1) * 3
        set(value) = sharedPreferences.edit().putInt("weeklyEfonaNoti", value).apply()
    var argos1Noti: Int
        get() = sharedPreferences.getInt("argos1Noti", 1)
        set(value) = sharedPreferences.edit().putInt("argos1Noti", value).apply()
    var argos2Noti: Int
        get() = sharedPreferences.getInt("argos2Noti", 1)
        set(value) = sharedPreferences.edit().putInt("argos2Noti", value).apply()
    var argos3Noti: Int
        get() = sharedPreferences.getInt("argos3Noti", 1)
        set(value) = sharedPreferences.edit().putInt("argos3Noti", value).apply()
    var ghostShipNoti: Int
        get() = sharedPreferences.getInt("ghostShipNoti", 1)
        set(value) = sharedPreferences.edit().putInt("ghostShipNoti", value).apply()
    var orehaNoti: Int
        get() = sharedPreferences.getInt("orehaNoti", 1)
        set(value) = sharedPreferences.edit().putInt("orehaNoti", value).apply()


    fun getRaidNotiList(): ArrayList<Int> = arrayListOf( bartanNoti, viakissNoti, koutosatonNoti, abrelshould12Noti, abrelshould34Noti, abrelshould56Noti )

    var bartanNoti: Int
        get() = sharedPreferences.getInt("bartanNormalNoti", 1)
        set(value) = sharedPreferences.edit().putInt("bartanNormalNoti", value).apply()
    var viakissNoti: Int
        get() = sharedPreferences.getInt("ViakissNormalNoti", 1)
        set(value) = sharedPreferences.edit().putInt("ViakissNormalNoti", value).apply()
    var koutosatonNoti: Int
        get() = sharedPreferences.getInt("koutosatonNormalNoti", 1)
        set(value) = sharedPreferences.edit().putInt("koutosatonNormalNoti", value).apply()
    var abrelshould12Noti: Int
        get() = sharedPreferences.getInt("abrelshould12Noti", 1)
        set(value) = sharedPreferences.edit().putInt("abrelshould12Noti", value).apply()
    var abrelshould34Noti: Int
        get() = sharedPreferences.getInt("abrelshould34Noti", 1)
        set(value) = sharedPreferences.edit().putInt("abrelshould34Noti", value).apply()
    var abrelshould56Noti: Int
        get() = sharedPreferences.getInt("abrelshould56Noti", 1)
        set(value) = sharedPreferences.edit().putInt("abrelshould56Noti", value).apply()

    fun getExpeditionNotiList(): ArrayList<Int> = arrayListOf( abyssDungeonNoti, koukosatonRehearsalNoti, abrelshouldDevajuNoti )

    var abyssDungeonNoti: Int
        get() = sharedPreferences.getInt("abyssDungeonNoti", 1)
        set(value) = sharedPreferences.edit().putInt("abyssDungeonNoti", value).apply()
    var koukosatonRehearsalNoti: Int
        get() = sharedPreferences.getInt("koukosatonRehearsalNoti", 1)
        set(value) = sharedPreferences.edit().putInt("koukosatonRehearsalNoti", value).apply()
    var abrelshouldDevajuNoti: Int
        get() = sharedPreferences.getInt("abrelshouldDevajuNoti", 1)
        set(value) = sharedPreferences.edit().putInt("abrelshouldDevajuNoti", value).apply()

}