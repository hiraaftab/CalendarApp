package com.example.calendar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val startTime: Long,
    val endTime: Long,
    val date: Long, // Date in millis (start of day)
    val color: Int = 0xFF6A5AE0.toInt() // Purple color from design
)


