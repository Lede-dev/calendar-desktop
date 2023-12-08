package net.ledestudio.calendar.manager

import net.ledestudio.calendar.event.CalendarEventHolder

interface CalendarManager {

    companion object {
        fun builder() = CalendarManagerBuilder()
    }

    fun getCalendarEventHolder(): CalendarEventHolder

}