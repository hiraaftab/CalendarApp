package com.example.calendar.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

/**
 * Utility class for calendar operations using Android's built-in Calendar and Java Time APIs
 */
object CalendarUtils {
    
    /**
     * Get the days to display in the calendar grid for a given month
     * Uses Java Time API (Android's built-in date/time library)
     */
    fun getDaysInMonth(yearMonth: YearMonth, locale: Locale): List<LocalDate?> {
        val firstDayOfMonth = yearMonth.atDay(1)
        val lastDayOfMonth = yearMonth.atEndOfMonth()
        
        // Get first day of week for locale
        val weekFields = WeekFields.of(locale)
        val firstDayOfWeek = weekFields.firstDayOfWeek
        
        // Calculate offset
        var startOffset = firstDayOfMonth.dayOfWeek.value - firstDayOfWeek.value
        if (startOffset < 0) startOffset += 7
        
        val dates = mutableListOf<LocalDate?>()
        
        // Add previous month's trailing days
        val prevMonth = yearMonth.minusMonths(1)
        val daysInPrevMonth = prevMonth.lengthOfMonth()
        for (i in (daysInPrevMonth - startOffset + 1)..daysInPrevMonth) {
            dates.add(prevMonth.atDay(i))
        }
        
        // Add current month's days
        for (day in 1..lastDayOfMonth.dayOfMonth) {
            dates.add(yearMonth.atDay(day))
        }
        
        // Add next month's leading days to complete the grid (42 cells = 6 weeks)
        val nextMonth = yearMonth.plusMonths(1)
        val remainingCells = 42 - dates.size
        for (day in 1..remainingCells) {
            dates.add(nextMonth.atDay(day))
        }
        
        return dates
    }
    
    /**
     * Get days of week headers based on locale
     */
    fun getDaysOfWeek(locale: Locale): List<DayOfWeek> {
        val weekFields = WeekFields.of(locale)
        val firstDayOfWeek = weekFields.firstDayOfWeek
        
        return (0..6).map { offset ->
            DayOfWeek.of(((firstDayOfWeek.value - 1 + offset) % 7) + 1)
        }
    }
    
    /**
     * Check if a date is in the current month
     */
    fun isInMonth(date: LocalDate, yearMonth: YearMonth): Boolean {
        return date.month == yearMonth.month && date.year == yearMonth.year
    }
    
    /**
     * Get the start of day in millis for Room database
     */
    fun getStartOfDayMillis(date: LocalDate): Long {
        return date.toEpochDay()
    }
    
    /**
     * Check if date is today
     */
    fun isToday(date: LocalDate): Boolean {
        return date == LocalDate.now()
    }
}



