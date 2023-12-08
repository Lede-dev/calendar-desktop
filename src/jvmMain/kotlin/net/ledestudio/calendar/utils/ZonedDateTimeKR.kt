package net.ledestudio.calendar.utils

import java.time.ZoneId
import java.time.ZonedDateTime

object ZonedDateTimeKR {

    private val zoneId = ZoneId.of("Asia/Seoul")

    fun zoneId(): ZoneId = zoneId

    fun now(): ZonedDateTime = ZonedDateTime.now(zoneId)

    fun of(year: Int, month: Int, day: Int): ZonedDateTime =
        of(year, month, day, 0, 0)

    fun of(year: Int, month: Int, day: Int, hour:Int, minute: Int): ZonedDateTime =
        ZonedDateTime.of(year, month, day, hour, minute, 0, 0, zoneId)
    
}