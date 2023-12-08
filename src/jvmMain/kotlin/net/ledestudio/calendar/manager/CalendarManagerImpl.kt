package net.ledestudio.calendar.manager

import net.ledestudio.calendar.event.CalendarEventHolder

class CalendarManagerImpl: CalendarManager {

    private lateinit var eventHolder: CalendarEventHolder

    fun setCalendarEventHolder(eventHolder: CalendarEventHolder) {
        this.eventHolder = eventHolder
    }

    override fun getCalendarEventHolder(): CalendarEventHolder {
        return eventHolder
    }

}