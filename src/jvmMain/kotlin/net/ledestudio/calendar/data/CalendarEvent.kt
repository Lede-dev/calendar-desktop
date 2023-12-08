package net.ledestudio.calendar.data

import java.time.ZonedDateTime
import java.util.UUID

data class CalendarEvent(
    val uuid: UUID,
    var startAt: ZonedDateTime,
    var expireAt: ZonedDateTime,
    var title: String,
    var contents: List<String>
)
