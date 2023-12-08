package net.ledestudio.calendar

import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import net.ledestudio.calendar.manager.CalendarManager

object CalendarDesktop {
    private lateinit var manager: CalendarManager

    fun setManager(manager: CalendarManager) {
        this.manager = manager
    }

    fun getManager() = manager

}

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    val manager = CalendarManager.builder().initCalendarEventHolder().build()
    CalendarDesktop.setManager(manager)

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}