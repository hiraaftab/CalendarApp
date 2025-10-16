package com.example.calendar.di

import android.content.Context
import com.example.calendar.data.local.CalendarDatabase
import com.example.calendar.data.local.dao.EventDao
import com.example.calendar.data.repository.EventRepositoryImpl
import com.example.calendar.domain.repository.EventRepository
import com.example.calendar.domain.usecase.*
import com.example.calendar.presentation.viewmodel.CalendarViewModel

object AppModule {
    
    private var database: CalendarDatabase? = null
    
    fun provideDatabase(context: Context): CalendarDatabase {
        return database ?: CalendarDatabase.getDatabase(context).also { database = it }
    }
    
    fun provideEventDao(context: Context): EventDao {
        return provideDatabase(context).eventDao()
    }
    
    fun provideEventRepository(context: Context): EventRepository {
        return EventRepositoryImpl(provideEventDao(context))
    }
    
    fun provideGetEventsByDateUseCase(context: Context): GetEventsByDateUseCase {
        return GetEventsByDateUseCase(provideEventRepository(context))
    }
    
    fun provideGetEventsInDateRangeUseCase(context: Context): GetEventsInDateRangeUseCase {
        return GetEventsInDateRangeUseCase(provideEventRepository(context))
    }
    
    fun provideGetDatesWithEventsUseCase(context: Context): GetDatesWithEventsUseCase {
        return GetDatesWithEventsUseCase(provideEventRepository(context))
    }
    
    fun provideCreateEventUseCase(context: Context): CreateEventUseCase {
        return CreateEventUseCase(provideEventRepository(context))
    }
    
    fun provideDeleteEventUseCase(context: Context): DeleteEventUseCase {
        return DeleteEventUseCase(provideEventRepository(context))
    }
    
    fun provideCalendarViewModel(context: Context): CalendarViewModel {
        return CalendarViewModel(
            getEventsByDateUseCase = provideGetEventsByDateUseCase(context),
            getDatesWithEventsUseCase = provideGetDatesWithEventsUseCase(context),
            createEventUseCase = provideCreateEventUseCase(context),
            deleteEventUseCase = provideDeleteEventUseCase(context)
        )
    }
}




