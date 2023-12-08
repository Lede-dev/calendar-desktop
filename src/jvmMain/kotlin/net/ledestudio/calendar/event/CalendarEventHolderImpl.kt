package net.ledestudio.calendar.event

import net.ledestudio.calendar.data.CalendarEvent
import net.ledestudio.calendar.storage.ObjectFileStorage
import net.ledestudio.calendar.utils.ZonedDateTimeKR.checkCross
import net.ledestudio.calendar.utils.ZonedDateTimeKR.of
import java.time.ZonedDateTime
import java.util.*

class CalendarEventHolderImpl(private val storage: ObjectFileStorage<CalendarEvent>): CalendarEventHolder {

    override fun getEventById(uuid: UUID): CalendarEvent {
        return storage.load(uuid.toString())
    }

    override fun getEventsInDay(day: ZonedDateTime): List<CalendarEvent> {
        val start = of(day.year, day.monthValue, day.dayOfMonth)
        val end = of(day.year, day.monthValue, day.dayOfMonth + 1)
        return getEventsBetweenDays(start, end)
    }

    override fun getEventsBetweenDays(start: ZonedDateTime, expire: ZonedDateTime): List<CalendarEvent> {
        return getEvents().filter { checkCross(it.startAt, it.expireAt, start, expire) }.toList()
    }

    override fun getEvents(): List<CalendarEvent> {
        return storage.load()
    }

    override fun addEvent(event: CalendarEvent) {
        storage.save(event.uuid.toString(), event)
    }

    override fun removeEvent(event: CalendarEvent) {
        removeEvent(event.uuid)
    }

    override fun removeEvent(uuid: UUID) {
        storage.delete(uuid.toString())
    }

    override fun clearEvents() {
        storage.clear()
    }
}