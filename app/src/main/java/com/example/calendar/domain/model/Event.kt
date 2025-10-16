package com.example.calendar.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Event(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val startTime: LocalTime,
    val endTime: LocalTime,
    val date: LocalDate,
    val color: Int = 0xFF6A5AE0.toInt()
)


