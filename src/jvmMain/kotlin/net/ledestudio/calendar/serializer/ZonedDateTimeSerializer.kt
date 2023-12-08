package net.ledestudio.calendar.serializer

import com.google.gson.*
import net.ledestudio.calendar.utils.ZonedDateTimeKR
import java.lang.reflect.Type
import java.time.ZonedDateTime

class ZonedDateTimeSerializer: JsonSerializer<ZonedDateTime>, JsonDeserializer<ZonedDateTime> {

    override fun serialize(src: ZonedDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (src != null) {
            return JsonPrimitive(ZonedDateTimeKR.serialize(src))
        }
        return JsonPrimitive("")
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ZonedDateTime {
        if (json != null) {
            val str = json.asJsonPrimitive.asString
            return if (str.isEmpty()) ZonedDateTime.now() else ZonedDateTimeKR.deserialize(str)
        }
        return ZonedDateTime.now()
    }

}
