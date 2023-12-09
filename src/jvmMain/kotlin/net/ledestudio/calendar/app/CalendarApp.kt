package net.ledestudio.calendar.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import net.ledestudio.calendar.utils.Colors
import net.ledestudio.calendar.utils.ZonedDateTimeKR
import kotlin.system.exitProcess


object CalendarApp {

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun App() {
        val datePickerState = rememberDatePickerState()
        if (datePickerState.selectedDateMillis == null) {
            // kst 오차 반영
            val kstDiff = 9 * 60 * 60 * 1000
            datePickerState.setSelection(ZonedDateTimeKR.now().toInstant().toEpochMilli() + kstDiff)
        }

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
                CalendarTitle(datePickerState)
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CalendarTitle(datePickerState: DatePickerState) {
        var sheetState by remember{ mutableStateOf(false) }
        val selectedDate = ZonedDateTimeKR.getLocalDateFromEpochMillis(datePickerState.selectedDateMillis)

        // calendar title
        IconButton(
            onClick = { sheetState = true },
            modifier = Modifier
                .fillMaxSize()
                .padding(350.dp, 10.dp, 350.dp, 0.dp)
                // .background(Color.Red)
        ) {
            Row {
                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(200.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(Colors.calendarTitleBackground),
                    text = "${selectedDate.year}",
                    textAlign = TextAlign.Center,
                    fontSize = 42.sp,
                    color = Colors.navIcon
                )

                Box(modifier = Modifier.fillMaxHeight().width(50.dp))

                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(200.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(Colors.calendarTitleBackground),
                    text = "${selectedDate.month.value}",
                    textAlign = TextAlign.Center,
                    fontSize = 42.sp,
                    color = Colors.navIcon
                )
            }
        }

        // calendar dialog and select date
        if (sheetState) {
            Dialog(
                onDismissRequest = {sheetState = false},
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false,
                        title = null,
                        headline = null
                    )
                }
            }
        }

    }

    @Composable
    private fun CalendarBody() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 10.dp, 20.dp, 20.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(Colors.navBackground)
        ) {

        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomSheetDatePicker(
        state: DatePickerState,
        sheetState: SheetState = rememberModalBottomSheetState(),
        onDismissRequest: () -> Unit
    ) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            DatePicker(
                state = state,
                showModeToggle = false,
                title = null,
                headline = null
            )
        }
    }

}