package net.ledestudio.calendar.manager

import net.ledestudio.calendar.event.CalendarEventHolderImpl
import net.ledestudio.calendar.storage.CalendarEventStorage
import java.nio.file.Path

class CalendarManagerBuilder {

    private val manager = CalendarManagerImpl()

    fun initCalendarEventHolder(): CalendarManagerBuilder {
        val storage = CalendarEventStorage(Path.of(""))
        val holder = CalendarEventHolderImpl(storage)
        manager.setCalendarEventHolder(holder)
        return this
    }

    fun build() = manager

}