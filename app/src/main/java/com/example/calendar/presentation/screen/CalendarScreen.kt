package com.example.calendar.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.R
import com.example.calendar.presentation.components.CalendarComponent
import com.example.calendar.presentation.components.EventListItem
import com.example.calendar.presentation.viewmodel.CalendarUiState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun CalendarScreen(
    uiState: CalendarUiState,
    onDateSelected: (LocalDate) -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    locale: Locale,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
            // Calendar at top
            CalendarComponent(
                currentMonth = uiState.currentMonth,
                selectedDate = uiState.selectedDate,
                datesWithEvents = uiState.datesWithEvents,
                onDateSelected = onDateSelected,
                onPreviousMonth = onPreviousMonth,
                onNextMonth = onNextMonth,
                locale = locale,
                modifier = Modifier.fillMaxWidth()
            )

            // Events list - takes all remaining space
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Events or empty state
                if (uiState.eventsForSelectedDate.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.EventNote,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    tint = Color(0xFFD0D0D0)
                                )
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    stringResource(R.string.no_events),
                                    color = Color(0xFF9E9E9E),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                } else {
                    items(
                        items = uiState.eventsForSelectedDate,
                        key = { it.id }
                    ) { event ->
                        EventListItem(
                            event = event,
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
        }
    }
