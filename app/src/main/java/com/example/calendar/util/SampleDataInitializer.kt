package com.example.calendar.util

import com.example.calendar.domain.model.Event
import com.example.calendar.domain.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class SampleDataInitializer(
    private val repository: EventRepository,
    private val scope: CoroutineScope
) {
    fun initializeSampleEvents() {
        scope.launch {
            val today = LocalDate.now()
            
            val sampleEvents = listOf(
                Event(
                    title = "Design new UX flow for Michael",
                    description = "Start from screen 16",
                    startTime = LocalTime.of(10, 0),
                    endTime = LocalTime.of(13, 0),
                    date = today.plusDays(0),
                    color = 0xFF6A5AE0.toInt()
                ),
                Event(
                    title = "Brainstorm with the team",
                    description = "Define the problem or question that...",
                    startTime = LocalTime.of(14, 0),
                    endTime = LocalTime.of(15, 0),
                    date = today.plusDays(0),
                    color = 0xFF4ECDC4.toInt()
                ),
                Event(
                    title = "Workout with Ella",
                    description = "We will do the legs and back workout",
                    startTime = LocalTime.of(19, 0),
                    endTime = LocalTime.of(20, 0),
                    date = today.plusDays(0),
                    color = 0xFFFF6B6B.toInt()
                ),
                Event(
                    title = "Team Meeting",
                    description = "Monthly sync meeting",
                    startTime = LocalTime.of(9, 0),
                    endTime = LocalTime.of(10, 0),
                    date = today.plusDays(2),
                    color = 0xFFFFD93D.toInt()
                ),
                Event(
                    title = "Lunch with Client",
                    description = "Discuss project requirements",
                    startTime = LocalTime.of(12, 30),
                    endTime = LocalTime.of(14, 0),
                    date = today.plusDays(5),
                    color = 0xFF95E1D3.toInt()
                ),
                Event(
                    title = "Code Review",
                    description = "Review pending PRs",
                    startTime = LocalTime.of(15, 0),
                    endTime = LocalTime.of(16, 30),
                    date = today.plusDays(8),
                    color = 0xFF6A5AE0.toInt()
                )
            )
            
            sampleEvents.forEach { event ->
                repository.insertEvent(event)
            }
        }
    }
}



