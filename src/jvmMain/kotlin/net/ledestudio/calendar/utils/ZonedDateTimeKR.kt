package net.ledestudio.calendar.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object ZonedDateTimeKR {

    private val zoneId = ZoneId.of("Asia/Seoul")
    private val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss Z")

    fun zoneId(): ZoneId = zoneId

    fun formatter(): DateTimeFormatter = formatter

    fun now(): ZonedDateTime = ZonedDateTime.now(zoneId)

    fun of(year: Int, month: Int, day: Int): ZonedDateTime =
        of(year, month, day, 0, 0)

    fun of(year: Int, month: Int, day: Int, hour:Int, minute: Int): ZonedDateTime =
        ZonedDateTime.of(year, month, day, hour, minute, 0, 0, zoneId)

    fun of(dateTime: LocalDateTime): ZonedDateTime = ZonedDateTime.of(dateTime, zoneId)

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
        (this.isEqual(first) || this.isAfter(first)) && (this.isEqual(second) || this.isBefore(second))

    fun serialize(time: ZonedDateTime): String {
        return time.format(formatter)
    }

    fun deserialize(str: String): ZonedDateTime {
        return ZonedDateTime.parse(str, formatter)
    }

    fun getLocalDateFromEpochMillis(millis: Long?): LocalDateTime {
        return Instant.ofEpochMilli(millis ?: 0)
            .atZone(zoneId())
            .toLocalDateTime()
    }

}