package net.ledestudio.calendar.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.ledestudio.calendar.utils.Colors
import kotlin.system.exitProcess


object CalendarApp {

    @Preview
    @Composable
    fun App() {
        Column(modifier = Modifier.fillMaxSize()) {

            // appbar
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Colors.navBackground,
                contentColor = Colors.navIcon,
                navigationIcon = {
                    IconButton(onClick = { /* Handle menu button click */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }

                    IconButton(onClick = { /* Handle search button click */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            exitProcess(0)
                        }
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Exit")
                    }
                }
            )

            // calendar title
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1F)
                    .background(Colors.calendarBackground)
            ) {
                CalendarTitle()
            }

            // calendar content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1.0F)
                    .background(Colors.calendarBackground)
            ) {
                CalendarBody()
            }
        }
    }

    @Composable
    fun CalendarTitle() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(300.dp, 10.dp, 300.dp, 0.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(Color.Red)
        ) {

        }
    }

    @Composable
    fun CalendarBody() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 10.dp, 20.dp, 20.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(Color.Blue)
        ) {

        }
    }
}