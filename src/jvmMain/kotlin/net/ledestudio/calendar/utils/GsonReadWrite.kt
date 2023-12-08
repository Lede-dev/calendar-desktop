package net.ledestudio.calendar.utils

import com.google.gson.GsonBuilder
import net.ledestudio.calendar.utils.serializer.ZonedDateTimeSerializer
import java.io.File
import java.nio.charset.StandardCharsets
import java.time.ZonedDateTime

object GsonReadWrite {

    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeSerializer())
        .create()

    fun <T> File.readObject(clazz: Class<T>): T = gson.fromJson(this.readText(StandardCharsets.UTF_8), clazz)

    fun <T> File. writeObject(obj: T) = this.writeText(gson.toJson(obj), StandardCharsets.UTF_8)

}