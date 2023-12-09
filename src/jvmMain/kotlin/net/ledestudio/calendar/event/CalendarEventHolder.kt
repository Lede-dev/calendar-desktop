package net.ledestudio.calendar.event

import net.ledestudio.calendar.data.CalendarEvent
import java.time.ZonedDateTime
import java.util.*

interface CalendarEventHolder {

    fun getEventById(uuid: UUID): CalendarEvent

    fun getEventsInDay(day: ZonedDateTime): List<CalendarEvent>

    fun getEventsBetweenDays(start: ZonedDateTime, expire: ZonedDateTime): List<CalendarEvent>

    fun getEvents(): List<CalendarEvent>

    fun addEvent(event: CalendarEvent)

    fun removeEvent(event: CalendarEvent)

    fun removeEvent(uuid: UUID)

    fun updateEvent(event: CalendarEvent) {
        removeEvent(event)
        addEvent(event)
    }

    fun clearEvents()

}