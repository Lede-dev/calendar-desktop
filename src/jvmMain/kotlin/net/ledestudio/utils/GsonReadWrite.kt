package net.ledestudio.utils

import com.google.gson.GsonBuilder
import java.io.File
import java.nio.charset.StandardCharsets

object GsonReadWrite {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    fun <T> File.readObject(clazz: Class<T>): T = gson.fromJson(this.readText(StandardCharsets.UTF_8), clazz)

    fun <T> File. writeObject(obj: T) = this.writeText(gson.toJson(obj), StandardCharsets.UTF_8)

}