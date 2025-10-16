package com.example.calendar.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.util.CalendarUtils
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun CalendarComponent(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    datesWithEvents: Set<LocalDate>,
    onDateSelected: (LocalDate) -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    modifier: Modifier = Modifier,
    locale: Locale = Locale.getDefault()
) {
    var offsetX by remember { mutableStateOf(0f) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 24.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (offsetX > 100) {
                            onPreviousMonth()
                        } else if (offsetX < -100) {
                            onNextMonth()
                        }
                        offsetX = 0f
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        offsetX += dragAmount
                    }
                )
            }
    ) {
        // Month Header with navigation
        MonthHeader(
            currentMonth = currentMonth,
            onPreviousMonth = onPreviousMonth,
            onNextMonth = onNextMonth,
            locale = locale
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Day of week headers
        DayOfWeekHeader(locale = locale)

        Spacer(modifier = Modifier.height(8.dp))

        // Calendar grid
        CalendarGrid(
            currentMonth = currentMonth,
            selectedDate = selectedDate,
            datesWithEvents = datesWithEvents,
            onDateSelected = onDateSelected,
            locale = locale
        )
    }
}

@Composable
private fun MonthHeader(
    currentMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    locale: Locale
) {
    val isRtl = locale.language == "ar"
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = if (isRtl) onNextMonth else onPreviousMonth) {
            Icon(
                imageVector = if (isRtl) Icons.Filled.ChevronRight else Icons.Filled.ChevronLeft,
                contentDescription = "Previous month",
                tint = Color(0xFF1E1E1E),
                modifier = Modifier.size(28.dp)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = currentMonth.month.getDisplayName(TextStyle.FULL, locale)
                    .replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E1E)
            )
            Text(
                text = currentMonth.year.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF9E9E9E)
            )
        }

        IconButton(onClick = if (isRtl) onPreviousMonth else onNextMonth) {
            Icon(
                imageVector = if (isRtl) Icons.Filled.ChevronLeft else Icons.Filled.ChevronRight,
                contentDescription = "Next month",
                tint = Color(0xFF1E1E1E),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun DayOfWeekHeader(locale: Locale) {
    // Use Android's built-in calendar utilities to get proper day order for locale
    val daysOfWeek = CalendarUtils.getDaysOfWeek(locale)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        daysOfWeek.forEach { day ->
            Text(
                text = day.getDisplayName(TextStyle.SHORT, locale),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF9E9E9E),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun CalendarGrid(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    datesWithEvents: Set<LocalDate>,
    onDateSelected: (LocalDate) -> Unit,
    locale: Locale
) {
    // Use Android's built-in calendar utilities to generate the calendar grid
    val dates = CalendarUtils.getDaysInMonth(currentMonth, locale)

    Column {
        dates.chunked(7).forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                week.forEach { date ->
                    if (date != null) {
                        CalendarDay(
                            date = date,
                            isSelected = date == selectedDate,
                            isCurrentMonth = CalendarUtils.isInMonth(date, currentMonth),
                            hasEvents = datesWithEvents.contains(date),
                            isToday = CalendarUtils.isToday(date),
                            onDateSelected = onDateSelected,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarDay(
    date: LocalDate,
    isSelected: Boolean,
    isCurrentMonth: Boolean,
    hasEvents: Boolean,
    isToday: Boolean,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = "scale"
    )

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(
                    if (isSelected) Color(0xFF6A5AE0)
                    else Color.Transparent
                )
                .clickable { onDateSelected(date) }
                .padding(4.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = when {
                    isSelected -> Color.White
                    isToday && isCurrentMonth -> Color(0xFF6A5AE0)
                    isCurrentMonth -> Color(0xFF1E1E1E)
                    else -> Color(0xFFD0D0D0)
                },
                fontWeight = when {
                    isSelected -> FontWeight.Bold
                    isToday -> FontWeight.Bold
                    else -> FontWeight.Normal
                },
                textAlign = TextAlign.Center
            )

            if (hasEvents && isCurrentMonth) {
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val eventCount = minOf(3, 1) // Show up to 3 dots
                    repeat(eventCount) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) Color.White
                                    else Color(0xFF6A5AE0)
                                )
                        )
                        if (it < eventCount - 1) {
                            Spacer(modifier = Modifier.width(2.dp))
                        }
                    }
                }
            }
        }
    }
}
