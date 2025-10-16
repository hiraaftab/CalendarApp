package com.example.calendar.data.repository

import com.example.calendar.data.local.dao.EventDao
import com.example.calendar.data.local.entity.EventEntity
import com.example.calendar.domain.model.Event
import com.example.calendar.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class EventRepositoryImpl(
    private val eventDao: EventDao
) : EventRepository {
    
    override fun getEventsByDate(date: LocalDate): Flow<List<Event>> {
        return eventDao.getEventsByDate(date.toEpochMilli())
            .map { entities -> entities.map { it.toDomainModel() } }
    }
    
    override fun getEventsInDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Event>> {
        return eventDao.getEventsInDateRange(startDate.toEpochMilli(), endDate.toEpochMilli())
            .map { entities -> entities.map { it.toDomainModel() } }
    }
    
    override fun getDatesWithEvents(startDate: LocalDate, endDate: LocalDate): Flow<List<LocalDate>> {
        return eventDao.getDatesWithEvents(startDate.toEpochMilli(), endDate.toEpochMilli())
            .map { dates -> dates.map { it.toLocalDate() } }
    }
    
    override suspend fun getEventById(eventId: Long): Event? {
        return eventDao.getEventById(eventId)?.toDomainModel()
    }
    
    override suspend fun insertEvent(event: Event): Long {
        return eventDao.insertEvent(event.toEntity())
    }
    
    override suspend fun updateEvent(event: Event) {
        eventDao.updateEvent(event.toEntity())
    }
    
    override suspend fun deleteEvent(event: Event) {
        eventDao.deleteEvent(event.toEntity())
    }
    
    override suspend fun deleteAllEvents() {
        eventDao.deleteAllEvents()
    }
    
    // Extension functions for conversion
    private fun EventEntity.toDomainModel(): Event {
        return Event(
            id = id,
            title = title,
            description = description,
            startTime = startTime.toLocalTime(),
            endTime = endTime.toLocalTime(),
            date = date.toLocalDate(),
            color = color
        )
    }
    
    private fun Event.toEntity(): EventEntity {
        return EventEntity(
            id = id,
            title = title,
            description = description,
            startTime = startTime.toEpochMilli(),
            endTime = endTime.toEpochMilli(),
            date = date.toEpochMilli(),
            color = color
        )
    }
    
    private fun LocalDate.toEpochMilli(): Long {
        return this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
    
    private fun Long.toLocalDate(): LocalDate {
        return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
    }
    
    private fun LocalTime.toEpochMilli(): Long {
        // Store time as milliseconds from midnight
        return (this.hour * 3600000L + this.minute * 60000L + this.second * 1000L)
    }
    
    private fun Long.toLocalTime(): LocalTime {
        // Convert milliseconds from midnight back to LocalTime
        val hours = (this / 3600000).toInt()
        val minutes = ((this % 3600000) / 60000).toInt()
        val seconds = ((this % 60000) / 1000).toInt()
        return LocalTime.of(hours, minutes, seconds)
    }
}

