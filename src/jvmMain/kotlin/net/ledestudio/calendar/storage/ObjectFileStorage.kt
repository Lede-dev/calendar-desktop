package net.ledestudio.calendar.storage

import java.nio.file.Path

interface ObjectFileStorage<T> {

    fun getPath(): Path

    fun save(name: String, obj: T)

    fun load(name: String): T

    fun load(): List<T>

    fun delete(name: String)

    fun clear()

}