package com.example.calendar.domain.usecase

import com.example.calendar.domain.model.Event
import com.example.calendar.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetEventsByDateUseCase(
    private val repository: EventRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<Event>> {
        return repository.getEventsByDate(date)
    }
}

