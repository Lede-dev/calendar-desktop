package net.ledestudio.data

import java.time.ZonedDateTime

data class CalendarEvent(
    var startAt: ZonedDateTime,
    var expireAt: ZonedDateTime,
    var title: String,
    var contents: List<String>
)
