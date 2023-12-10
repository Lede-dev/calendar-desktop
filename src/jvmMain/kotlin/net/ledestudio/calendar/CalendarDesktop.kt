package net.ledestudio.calendar

//import androidx.compose.ui.Alignment
//import androidx.compose.ui.unit.DpSize
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.window.Window
//import androidx.compose.ui.window.WindowPosition
//import androidx.compose.ui.window.application
//import androidx.compose.ui.window.rememberWindowState
//import net.ledestudio.calendar.app.CalendarApp
import net.ledestudio.calendar.data.CalendarEventBuilder
import net.ledestudio.calendar.manager.CalendarManager
import net.ledestudio.calendar.utils.ZonedDateTimeKR
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.ZonedDateTime

object CalendarDesktop {
    private lateinit var manager: CalendarManager

    fun setManager(manager: CalendarManager) {
        this.manager = manager
    }

    fun getManager() = manager

}

enum class CalendarCommand(val num: Int) {

    ERROR(0),
    EXIT(1),
    DISPLAY(2),
    EVENT_ADD(3),
    EVENT_DISPLAY(4),
    EVENT_DELETE(5),
    EVENT_EDIT(6),
    EVENT_SEARCH(7);

    companion object {
        fun getByNum(otherNum: Int): CalendarCommand {
            values().forEach {
                if (it.num == otherNum) {
                    return it
                }
            }
            return ERROR
        }
    }
}

fun main() {
    // setup calendar manager
    val manager = CalendarManager.builder().initCalendarEventHolder().build()
    CalendarDesktop.setManager(manager)

    var exit = true
    while (exit) {
        println("메뉴 번호를 선택하세요 (1:종료, 2:조회, 3:이벤트 추가, 4:이벤트 조회, 5:이벤트 제거, 6:이벤트 수정, 7:이벤트 검색).")
        when(readlnOrNull()?.let { CalendarCommand.getByNum(it.toInt()) }) {
            CalendarCommand.EXIT -> {
                println("캘린더 종료.")
                exit = false
            }
            CalendarCommand.DISPLAY -> displayCalendar()
            CalendarCommand.EVENT_ADD -> addEvent()
            CalendarCommand.EVENT_DISPLAY -> displayEvent()
            CalendarCommand.EVENT_DELETE -> deleteEvent()
            CalendarCommand.EVENT_EDIT -> editEvent()
            CalendarCommand.EVENT_SEARCH -> searchEvent()
            else -> println("잘못된 메뉴 번호를 입력하였습니다.")
        }
    }
}

fun displayCalendar() {
    println("조회할 달력의 년도와 월을 입력하세요 (띄어쓰기로 구분).")
    readlnOrNull()?.let {
        val read = it.split(" ")
        val yearMonth = YearMonth.of(read[0].toInt(), read[1].toInt())
        val firstDay = yearMonth.atDay(1)
        val lastDay = yearMonth.atEndOfMonth()
        val firstDayOfWeek = firstDay.dayOfWeek

        val calendarEventHolder = CalendarDesktop.getManager().getCalendarEventHolder()

        println()
        println("  %-7s  %-7s  %-7s  %-7s  %-7s  %-7s  %-7s".format("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"))

        // print empty date
        var printIndex = 0
        if (firstDayOfWeek != DayOfWeek.SUNDAY) {
            for (i in 1..firstDayOfWeek.value) {
                print("  %-7s".format(""))
                printIndex++
            }
        }

        // print other dates
        var printDays = 1
        while (printDays <= lastDay.dayOfMonth) {
            if (printIndex <= 6) {
                val day = ZonedDateTimeKR.of(yearMonth.year, yearMonth.monthValue, printDays)
                val eventsInDay = calendarEventHolder.getEventsInDay(day)
                val printValue = (if (printDays / 10 < 1) "0${printDays}" else printDays.toString()) +
                        (if (eventsInDay.isNotEmpty()) "(${eventsInDay.size})" else "")
                print("  %-7s".format(printValue))
                printDays++
                printIndex++
            } else {
                printIndex = 0
                println()
            }
        }

        println()
        println()
    }
}

fun addEvent() {
    val builder = CalendarEventBuilder()

    println("이벤트 제목을 입력하세요.")
    readlnOrNull()?.let { builder.title(it) }

    println("이벤트 시작 시간을 입력하세요 (년 월 일 시 분 띄어쓰기로 구분).")
    readlnOrNull()?.let { builder.startAt(readZonedDateTime(it)) }

    println("이벤트 종료 시간을 입력하세요 (년 월 일 시 분 띄어쓰기로 구분).")
    readlnOrNull()?.let  {builder.expireAt(readZonedDateTime(it)) }

    println("이벤트 세부사항을 입력하세요 (| 입력으로 줄 구분)")
    readlnOrNull()?.let { builder.contents(it.split("|")) }

    val event = builder.build()
    CalendarDesktop.getManager().getCalendarEventHolder().addEvent(event)
    println("이벤트 저장 완료.")
    println()
}

fun displayEvent() {
    println("조회할 이벤트 날짜를 입력하세요 (년 월 일 띄어쓰기로 구분).")
    readlnOrNull()?.let {
        val read = it.split(" ")
        val date = ZonedDateTimeKR.of(read[0].toInt(), read[1].toInt(), read[2].toInt())
        val events = CalendarDesktop.getManager().getCalendarEventHolder().getEventsInDay(date)
        if (events.isEmpty()) {
            println("등록된 이벤트가 없습니다.")
        } else {
            events.forEach { event -> event.print() }
        }
    }

    println("이벤트 조회 완료.")
    println()
}

fun deleteEvent() {
    println("제거할 이벤트 날짜를 입력하세요 (년 월 일 띄어쓰기로 구분).")
    readlnOrNull()?.let {
        val read = it.split(" ")
        val date = ZonedDateTimeKR.of(read[0].toInt(), read[1].toInt(), read[2].toInt())
        val holder = CalendarDesktop.getManager().getCalendarEventHolder()
        val events = holder.getEventsInDay(date)
        if (events.isEmpty()) {
            println("제거할 이벤트가 없습니다.")
            println()
            return
        }

        events.forEachIndexed { index, event ->
            println("[번호]: $index")
            event.print()
            println()
        }

        println("제거할 이벤트 번호를 입력해 주세요 (0 ~ ${events.size - 1}).")
        readlnOrNull()?.let {eventIndex ->
            val event = events[eventIndex.toInt()]
            holder.removeEvent(event)
            println("이벤트 제거 완료.")
            println()
        }
    }
}

fun editEvent() {
    println("수정할 이벤트 날짜를 입력하세요 (년 월 일 띄어쓰기로 구분).")
    readlnOrNull()?.let {
        val read = it.split(" ")
        val date = ZonedDateTimeKR.of(read[0].toInt(), read[1].toInt(), read[2].toInt())
        val holder = CalendarDesktop.getManager().getCalendarEventHolder()
        val events = holder.getEventsInDay(date)
        if (events.isEmpty()) {
            println("수정할 이벤트가 없습니다.")
            println()
            return;
        }

        events.forEachIndexed { index, event ->
            println("[번호]: $index")
            event.print()
            println()
        }

        println("수정할 이벤트 번호를 입력해 주세요 (0 ~ ${events.size - 1}).")
        readlnOrNull()?.let {eventIndex ->
            val event = events[eventIndex.toInt()]

            println("이벤트 제목을 입력하세요.")
            readlnOrNull()?.let { title -> event.title = title }

            println("이벤트 시작 시간을 입력하세요 (년 월 일 시 분 띄어쓰기로 구분).")
            readlnOrNull()?.let { start -> event.startAt = readZonedDateTime(start) }

            println("이벤트 종료 시간을 입력하세요 (년 월 일 시 분 띄어쓰기로 구분).")
            readlnOrNull()?.let { expire -> event.expireAt = readZonedDateTime(expire) }

            println("이벤트 세부사항을 입력하세요 (| 입력으로 줄 구분)")
            readlnOrNull()?.let { contents ->
                event.contents = contents.split("|")
            }

            holder.updateEvent(event)
            println("이벤트 수정 완료.")
        }
    }
}

fun searchEvent() {
    println("검색할 이벤트 제목을 입력하세요.")
    readlnOrNull()?.let {
        val holder = CalendarDesktop.getManager().getCalendarEventHolder()
        val events = holder.getEvents().filter { event -> event.title.contains(it) }

        if (events.isEmpty()) {
            println("검색된 이벤트가 없습니다.")
            println()
            return;
        }

        events.forEach { event -> event.print() }
        println()
    }
}

private fun readZonedDateTime(timeStr: String): ZonedDateTime {
    val read = timeStr.split(" ")
    return ZonedDateTimeKR.of(read[0].toInt(), read[1].toInt(), read[2].toInt(), read[3].toInt(), read[4].toInt())
}

//fun main() = application {
//    // setup calendar manager
//    val manager = CalendarManager.builder().initCalendarEventHolder().build()
//    CalendarDesktop.setManager(manager)
//
//    // setup window state
//    val state = rememberWindowState(
//        size = DpSize(1200.dp, 800.dp),
//        position = WindowPosition(Alignment.Center)
//    )
//
//    // create and start app
//    Window(
//        title = "Calendar Desktop",
//        onCloseRequest = ::exitApplication,
//        state = state,
//        resizable = false
//    ) {
//        CalendarApp.App()
//    }
//}