package net.ledestudio.calendar

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import net.ledestudio.calendar.app.CalendarApp
import net.ledestudio.calendar.manager.CalendarManager

object CalendarDesktop {
    private lateinit var manager: CalendarManager

    fun setManager(manager: CalendarManager) {
        this.manager = manager
    }

    fun getManager() = manager

}

fun main() = application {
    // setup calendar manager
    val manager = CalendarManager.builder().initCalendarEventHolder().build()
    CalendarDesktop.setManager(manager)

    // setup window state
    val state = rememberWindowState(
        size = DpSize(1200.dp, 800.dp),
        position = WindowPosition(Alignment.Center)
    )

    // create and start app
    Window(
        title = "Calendar Desktop",
        onCloseRequest = ::exitApplication,
        state = state
    ) {
        CalendarApp.App()
    }
}