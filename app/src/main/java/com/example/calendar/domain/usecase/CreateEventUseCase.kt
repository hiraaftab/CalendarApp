package com.example.calendar.domain.usecase

import com.example.calendar.domain.model.Event
import com.example.calendar.domain.repository.EventRepository

class CreateEventUseCase(
    private val repository: EventRepository
) {
    suspend operator fun invoke(event: Event): Long {
        return repository.insertEvent(event)
    }
}



