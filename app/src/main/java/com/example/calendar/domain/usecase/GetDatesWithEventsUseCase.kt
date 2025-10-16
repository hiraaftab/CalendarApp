package com.example.calendar.domain.usecase

import com.example.calendar.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetDatesWithEventsUseCase(
    private val repository: EventRepository
) {
    operator fun invoke(startDate: LocalDate, endDate: LocalDate): Flow<List<LocalDate>> {
        return repository.getDatesWithEvents(startDate, endDate)
    }
}



