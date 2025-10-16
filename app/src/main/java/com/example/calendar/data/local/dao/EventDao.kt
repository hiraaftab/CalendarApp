package com.example.calendar.data.local.dao

import androidx.room.*
import com.example.calendar.data.local.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    
    @Query("SELECT * FROM events WHERE date = :date ORDER BY startTime ASC")
    fun getEventsByDate(date: Long): Flow<List<EventEntity>>
    
    @Query("SELECT * FROM events WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC, startTime ASC")
    fun getEventsInDateRange(startDate: Long, endDate: Long): Flow<List<EventEntity>>
    
    @Query("SELECT DISTINCT date FROM events WHERE date BETWEEN :startDate AND :endDate")
    fun getDatesWithEvents(startDate: Long, endDate: Long): Flow<List<Long>>
    
    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEventById(eventId: Long): EventEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity): Long
    
    @Update
    suspend fun updateEvent(event: EventEntity)
    
    @Delete
    suspend fun deleteEvent(event: EventEntity)
    
    @Query("DELETE FROM events")
    suspend fun deleteAllEvents()
}


