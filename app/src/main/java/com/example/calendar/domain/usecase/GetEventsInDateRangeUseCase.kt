package com.example.calendar.domain.usecase

import com.example.calendar.domain.model.Event
import com.example.calendar.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetEventsInDateRangeUseCase(
    private val repository: EventRepository
) {
    operator fun invoke(startDate: LocalDate, endDate: LocalDate): Flow<List<Event>> {
        return repository.getEventsInDateRange(startDate, endDate)
    }
}

