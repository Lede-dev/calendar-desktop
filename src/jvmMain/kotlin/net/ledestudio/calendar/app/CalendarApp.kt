package net.ledestudio.calendar.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import net.ledestudio.calendar.CalendarDesktop
import net.ledestudio.calendar.data.CalendarEvent
import net.ledestudio.calendar.utils.Colors
import net.ledestudio.calendar.utils.ZonedDateTimeKR
import java.time.Instant
import java.time.ZonedDateTime
import kotlin.system.exitProcess


object CalendarApp {

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun App() {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = Instant.now().toEpochMilli() + (9 * 60 * 60 * 1000) // kst +9
        )

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

            // calendar content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1.0F)
                    .background(Colors.calendarBackground)
            ) {
                CalendarContent(datePickerState)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CalendarContent(datePickerState: DatePickerState) {

        // initialize states
        val selectedDate = ZonedDateTimeKR.getLocalDateFromEpochMillis(datePickerState.selectedDateMillis)
        val zonedDateTime = ZonedDateTimeKR.of(selectedDate)

        var sheetState by remember{ mutableStateOf(false) }
        var eventDialogState by remember { mutableStateOf(false) }

        val events = CalendarDesktop.getManager().getCalendarEventHolder().getEventsInDay(zonedDateTime)
        val calendarEvents by remember { mutableStateOf(events.toMutableList()) }

        // create calendar box
        CalendarBox(
            zonedDateTime = zonedDateTime,
            datePickerState = datePickerState,
            getCalendarEvents = {
                calendarEvents.clear()
                calendarEvents.addAll(CalendarDesktop.getManager().getCalendarEventHolder().getEventsInDay(zonedDateTime))
                calendarEvents
            },
            updateSheetState = { newState -> sheetState = newState }
        )

        // create event dialog
        CalendarEventDialog(
            zonedDateTime = zonedDateTime,
            getSheetState = { sheetState },
            getEventDialogState = { eventDialogState },
            updateCalendarEvents = {
                calendarEvents.clear()
                calendarEvents.addAll(CalendarDesktop.getManager().getCalendarEventHolder().getEventsInDay(zonedDateTime))
                calendarEvents
            },
            updateSheetState = { newState -> sheetState = newState },
            updateEventDialogState = { newState -> eventDialogState = newState }
        )

    }

    @Composable
    private fun CalendarEventDetailButton(
        event: CalendarEvent,
        eventDialogState: () -> Boolean,
        updateEventDialogState: (Boolean) -> Unit,
        updateSheetState: (Boolean) -> Unit,
        updateCalendarEvents: () -> MutableList<CalendarEvent>
    ) {
        var title = event.title
        if (title.length > 65) {
            title = title.substring(0, 65) + "..."
        }

        IconButton(
            onClick = { updateEventDialogState(true) },
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.padding(0.dp, 15.dp, 0.dp, 0.dp)
            ) {
                Text(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .padding(15.dp, 0.dp, 15.dp, 0.dp),
                    text = title,
                    fontSize = 17.sp,
                    color = Color.White,
                )
            }
        }

        if (eventDialogState.invoke()) {
            Dialog(
                onDismissRequest = { updateEventDialogState(false) },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1000.dp)
                        .padding(80.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        // header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.05F)
                                .background(Colors.navBackground)
                        ) {
                            IconButton(
                                onClick = {
                                    CalendarDesktop.getManager().getCalendarEventHolder().removeEvent(event)
                                    updateCalendarEvents()
                                    updateEventDialogState(false)
                                    updateSheetState(false)
                                    updateSheetState(true)
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    modifier = Modifier.size(25.dp),
                                    tint = Colors.navIcon
                                )
                            }

                            Box(modifier = Modifier.fillMaxWidth(0.95F))

                            IconButton(
                                onClick = {
                                    // contents
                                    event.title = "타이틀 업데이트 완료"

                                    CalendarDesktop.getManager().getCalendarEventHolder().updateEvent(event)
                                    updateCalendarEvents()
                                    updateEventDialogState(false)
                                    updateSheetState(false)
                                    updateSheetState(true)
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Save",
                                    modifier = Modifier.size(25.dp),
                                    tint = Colors.navIcon
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CalendarBox(
        zonedDateTime: ZonedDateTime,
        datePickerState: DatePickerState,
        getCalendarEvents: () -> List<CalendarEvent>,
        updateSheetState: (Boolean) -> Unit
    ) {
        val events = getCalendarEvents.invoke()
        val eventsAmountText = if (events.isNotEmpty()) " (${events.size})" else ""
        val title = "${zonedDateTime.year}년 ${zonedDateTime.monthValue}월 ${zonedDateTime.dayOfMonth}일" + eventsAmountText

        Box(
            modifier = Modifier
                .fillMaxSize()
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
                    title = {
                        Row {
                            Box(modifier = Modifier.width(30.dp))

                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(0.dp, 30.dp, 0.dp, 0.dp),
                                text = title,
                                textAlign = TextAlign.Center,
                                fontSize = 45.sp,
                            )

                            Box(modifier = Modifier.width(30.dp))

                            IconButton(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(0.dp, 35.dp, 0.dp, 0.dp),
                                onClick = { updateSheetState(true) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Event",
                                    modifier = Modifier.size(55.dp)
                                )
                            }
                        }
                    },
                    headline = null
                )
            }
        }
    }

    @Composable
    private fun CalendarEventDialog(
        zonedDateTime: ZonedDateTime,
        getSheetState: () -> Boolean,
        getEventDialogState: () -> Boolean,
        updateCalendarEvents: () -> MutableList<CalendarEvent>,
        updateSheetState: (Boolean) -> Unit,
        updateEventDialogState: (Boolean) -> Unit
    ) {
        if (getSheetState.invoke()) {
            Dialog(
                onDismissRequest = { updateSheetState(false) },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1200.dp)
                        .padding(60.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.06F)
                                .background(Colors.navBackground)
                        ) {

                            // create event
                            IconButton(
                                onClick = {
                                    val newEvent = CalendarEvent.builder()
                                        .startAt(zonedDateTime)
                                        .expireAt(zonedDateTime)
                                        .build()
                                    CalendarDesktop.getManager().getCalendarEventHolder().addEvent(newEvent)
                                    updateCalendarEvents()
                                    updateSheetState(false)
                                    updateSheetState(true)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = "Add Event",
                                    modifier = Modifier.size(25.dp),
                                    tint = Colors.navIcon
                                )
                            }

                            Box(modifier = Modifier.fillMaxWidth(0.95F))

                            IconButton(
                                onClick = { updateSheetState(false) },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ExitToApp,
                                    contentDescription = "Exit Dialog",
                                    modifier = Modifier.size(25.dp),
                                    tint = Colors.navIcon
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Colors.calendarBackground)
                                .verticalScroll(rememberScrollState())
                        ) {
                            updateCalendarEvents.invoke().forEach { event ->
                                CalendarEventDetailButton(
                                    event = event,
                                    eventDialogState = getEventDialogState,
                                    updateEventDialogState = updateEventDialogState,
                                    updateSheetState = updateSheetState,
                                    updateCalendarEvents = updateCalendarEvents
                                )

                                LineDivider.HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }


}