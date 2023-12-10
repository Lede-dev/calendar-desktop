package net.ledestudio.calendar.data

import net.ledestudio.calendar.utils.ZonedDateTimeKR
import java.time.ZonedDateTime
import java.util.*

data class CalendarEvent(
    val uuid: UUID,
    var startAt: ZonedDateTime,
    var expireAt: ZonedDateTime,
    var title: String,
    var contents: List<String>
) {
    companion object {
        fun builder() = CalendarEventBuilder()
    }

    fun print() {
        println("[제목]: $title")
        println("[기간]: $startAt ~ $expireAt")
        println("[상세]:")
        contents.forEach { println(it) }
        println("")
    }
}

class CalendarEventBuilder {

    private val uuid = UUID.randomUUID()
    private var startAt: ZonedDateTime = ZonedDateTimeKR.now()
    private var expireAt: ZonedDateTime = ZonedDateTimeKR.now()
    private var title = "이름을 입력해 주세요"
    private var contents = mutableListOf<String>()

    fun startAt(startAt: ZonedDateTime): CalendarEventBuilder {
        this.startAt = startAt
        return this
    }

    fun expireAt(expireAt: ZonedDateTime): CalendarEventBuilder {
        this.expireAt = expireAt;
        return this
    }

    fun title(title: String): CalendarEventBuilder {
        this.title = title;
        return this;
    }

    fun contents(contents: List<String>): CalendarEventBuilder {
        this.contents = contents.toMutableList()
        return this;
    }

    fun build() = CalendarEvent(uuid, startAt, expireAt, title, contents)

}