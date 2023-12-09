package net.ledestudio.calendar.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import net.ledestudio.calendar.CalendarDesktop
import net.ledestudio.calendar.utils.Colors
import net.ledestudio.calendar.utils.ZonedDateTimeKR
import java.time.DayOfWeek
import java.time.Instant
import java.time.YearMonth
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

            // calendar title
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(0.1F)
//                    .background(Colors.calendarBackground)
//            ) {
//                CalendarTitle(datePickerState)
//            }

            // calendar content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1.0F)
                    .background(Colors.calendarBackground)
            ) {
                // CalendarBody(datePickerState)
                CalendarContentGrid(datePickerState)
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CalendarBody(datePickerState: DatePickerState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 10.dp, 20.dp, 20.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(Colors.navBackground)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    //.background(Color.Red)
            ) {
                // Weeks
                WeekTextRow(width = 163.dp)

                HorizontalDivider()

                // Calendar
                CalendarContentGrid(datePickerState)
            }
        }
    }

    @Composable
    private fun WeekTextRow(width: Dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.07F)
        ) {
            WeekText("Sun", width)
            VerticalDivider()
            WeekText("Mon", width)
            VerticalDivider()
            WeekText("Tue", width)
            VerticalDivider()
            WeekText("Wed", width)
            VerticalDivider()
            WeekText("Thu", width)
            VerticalDivider()
            WeekText("Fri", width)
            VerticalDivider()
            WeekText("Sat", width)
        }
    }

    @Composable
    private fun WeekText(text: String, width: Dp) {
        Text(
            modifier = Modifier
                .fillMaxHeight()
                .width(width), //.background(Color.Blue),
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = Colors.navIcon
        )
    }

    @Composable
    private fun VerticalDivider(color: Color = Color.White, thickness: Dp = 1.dp) {
        Divider(
            color = color,
            modifier = Modifier.fillMaxHeight().width(thickness)
        )
    }

    @Composable
    private fun HorizontalDivider(color: Color = Color.White, thickness: Dp = 1.dp) {
        Divider(
            color = color,
            thickness = thickness
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CalendarContentGrid(datePickerState: DatePickerState) {
        val selectedDate = ZonedDateTimeKR.getLocalDateFromEpochMillis(datePickerState.selectedDateMillis)

        val zonedDateTime = ZonedDateTimeKR.of(selectedDate)
        val events = CalendarDesktop.getManager().getCalendarEventHolder().getEventsInDay(zonedDateTime)

        val eventsAmountText = if (events.isNotEmpty()) " (${events.size})" else ""
        val title = "${selectedDate.year}년 ${selectedDate.monthValue}월 ${selectedDate.dayOfMonth}일" + eventsAmountText

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
                                onClick = {}
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

//            LazyVerticalGrid(
//                columns = GridCells.Fixed(7),
//                verticalArrangement = Arrangement.spacedBy(1.dp),
//                horizontalArrangement = Arrangement.spacedBy(1.dp)
//            ) {
//                val yearMonth = YearMonth.of(selectedDate.year, selectedDate.monthValue)
//                val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek
//                val lastDayOfWeek = yearMonth.atEndOfMonth().dayOfWeek
//
////                val data = mutableListOf<ZonedDateTime?>()
////                if (firstDayOfWeek != DayOfWeek.SUNDAY) {
////                    for (i in 1 .. (firstDayOfWeek.value)) {
////                        data.add(null)
////                    }
////                }
////
////                for (i in 1..selectedDate.da)
////
////
////                for (i in 1..42) {
////                    data.add(ZonedDateTimeKR.now())
////                }
////
////                // items(data) { item -> CalendarContent(item) }
////                itemsIndexed(items = listOf("1")) {index, day ->
////
////                }
//
//            }
        }
    }

    @Composable
    private fun CalendarContent(date: ZonedDateTime) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan)
        )
    }

}