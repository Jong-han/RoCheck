package com.jh.roachecklist

object Const {

    const val MAX_LEVEL = 10000

    const val INTERVAL: Long = 1000 * 60 * 60 * 24
    const val TEST_INTERVAL: Long = 1000 * 60 * 5


    object Favorite {

        const val MAIN = 2
        const val SUB = 1
        const val BARRACK = 0

    }

    object NotiState {

        const val YES = 1
        const val NO = 0

    }

    object Rest {

        const val MAX_REST_BONUS = 100
        const val DAILY_EFONA_COUNT = 3
        const val DAILY_GUARDIAN_COUNT = 2
        const val CHAOS_DUNGEON_COUNT = 2
        const val CONSUME_REST_BONUS = 20
        const val CHARGE_REST_BONUS = 10

    }

    object WorkType {

        const val DAILY = "DAILY"
        const val WEEKLY = "WEEKLY"
        const val RAID = "RAID"
        const val EXPEDITION = "EXPEDITION"
        const val EXPEDITION_DAILY = "EXPEDITION_DAILY"
        const val EXPEDITION_WEEKLY = "EXPEDITION_WEEKLY"

    }

    object DailyIndex {

        const val GUILD = 0
        const val DAILY_EFONA = 1
        const val DAILY_GUARDIAN = 2
        const val CHAOS_DUNGEON = 3

    }

    object DailyWork {

        const val GUILD = "길드 출석"
        const val DAILY_EFONA = "일일 에포나 의뢰"
        const val DAILY_GUARDIAN = "가디언 토벌"
        const val CHAOS_DUNGEON = "카오스 던전"

    }

    object WeeklyIndex {

        const val CHALLENGE_ABYSS_DUNGEON = 0
        const val CHALLENGE_GUARDIAN = 1
        const val WEEKLY_EFONA = 2
        const val ARGOS_1 = 3
        const val ARGOS_2 = 4
        const val ARGOS_3 = 5
        const val GHOST_SHIP = 6
        const val OREHA = 7

    }

    object WeeklyWork {

        const val WEEKLY_EFONA = "주간 에포나 의뢰"
        const val ARGOS = "아르고스"
        const val OREHA = "오레하의 우물"

    }

    object Raid {

        const val BALTAN = "발탄"
        const val VIAKISS = "비아키스"
        const val KOUKUSATON = "쿠크세이튼"
        const val ABRELSHOULD_1_2 = "아브렐슈드 1/2 관문"
        const val ABRELSHOULD_3_4 = "아브렐슈드 3/4 관문"
        const val ABRELSHOULD_5_6 = "아브렐슈드 5/6 관문"

    }

    object ExpeditionWeekly {

        const val CHALLENGE_GUARDIAN = "도전 가디언 토벌"
        const val CHALLENGE_ABYSS_DUNGEON = "도전 어비스 던전"
        const val KOUKUSATON_REHEARSAL = "쿠크세이튼 리허설"
        const val ABRELSHOULD_DEJAVU = "아브렐슈드 데자뷰"
        const val GHOST_SHIP = "유령선"

    }

    object ExpeditionDaily {

        const val FAVORABILITY = "호감도"
        const val ISLAND = "모험섬"
        const val FIELD_BOSS = "필드 보스"
        const val CHAOS_GATE = "카오스 게이트"

    }

}