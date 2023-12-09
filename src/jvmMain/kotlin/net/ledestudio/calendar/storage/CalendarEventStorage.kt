package net.ledestudio.calendar.storage

import net.ledestudio.calendar.data.CalendarEvent
import net.ledestudio.calendar.utils.GsonReadWrite.readObject
import net.ledestudio.calendar.utils.GsonReadWrite.writeObject
import java.io.File
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists

class CalendarEventStorage(private val dirPath: Path): ObjectFileStorage<CalendarEvent> {

    init {
        if (dirPath.notExists()) {
            dirPath.createDirectories()
        }
    }

    override fun getPath(): Path {
        return dirPath
    }

    override fun save(name: String, obj: CalendarEvent) {
        getFile(name).also {
            if (!it.exists()) {
                it.createNewFile()
            }
            it.writeObject(obj)
        }
    }

    override fun load(name: String): CalendarEvent {
        getFile(name).also {
            if (!it.exists()) {
                it.createNewFile()
            }
            return it.readObject(CalendarEvent::class.java)
        }
    }

    override fun load(): List<CalendarEvent> {
        val eventList = mutableListOf<CalendarEvent>()
        dirPath.toFile().listFiles()?.filterNotNull()?.forEach {
            eventList.add(it.readObject(CalendarEvent::class.java))
        }
        return eventList
    }

    override fun delete(name: String) {
        getFile(name).also { it.delete() }
    }

    override fun clear() {
        dirPath.toFile().listFiles()?.filterNotNull()?.forEach { it.delete() }
    }

    private fun getFile(name: String): File = File(dirPath.toString(), "$name.json")

}