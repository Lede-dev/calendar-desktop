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

    fun checkCross(
        firstStartTime: ZonedDateTime,
        firstExpireTime: ZonedDateTime,
        secondStartTime: ZonedDateTime,
        secondExpireTime: ZonedDateTime): Boolean
    {
        return firstStartTime.isInRange(secondStartTime, secondExpireTime) ||
                    firstExpireTime.isInRange(secondStartTime, secondExpireTime) ||
                        secondStartTime.isInRange(firstStartTime, firstExpireTime) ||
                            secondExpireTime.isInRange(firstStartTime, firstExpireTime)
    }

    fun ZonedDateTime.isInRange(first: ZonedDateTime, second: ZonedDateTime): Boolean =
        this.isEqual(first) || this.isEqual(second) || this.isAfter(first) || this.isBefore(second)
}