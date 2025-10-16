package com.example.calendar.domain.repository

import com.example.calendar.domain.model.Event
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface EventRepository {
    
    fun getEventsByDate(date: LocalDate): Flow<List<Event>>
    
    fun getEventsInDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Event>>
    
    fun getDatesWithEvents(startDate: LocalDate, endDate: LocalDate): Flow<List<LocalDate>>
    
    suspend fun getEventById(eventId: Long): Event?
    
    suspend fun insertEvent(event: Event): Long
    
    suspend fun updateEvent(event: Event)
    
    suspend fun deleteEvent(event: Event)
    
    suspend fun deleteAllEvents()
}

