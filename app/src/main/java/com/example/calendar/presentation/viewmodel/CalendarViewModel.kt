package com.example.calendar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.domain.model.Event
import com.example.calendar.domain.usecase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

data class CalendarUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val currentMonth: YearMonth = YearMonth.now(),
    val eventsForSelectedDate: List<Event> = emptyList(),
    val datesWithEvents: Set<LocalDate> = emptySet(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@OptIn(ExperimentalCoroutinesApi::class)
class CalendarViewModel(
    private val getEventsByDateUseCase: GetEventsByDateUseCase,
    private val getDatesWithEventsUseCase: GetDatesWithEventsUseCase,
    private val createEventUseCase: CreateEventUseCase,
    private val deleteEventUseCase: DeleteEventUseCase
) : ViewModel() {
    
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    private val _currentMonth = MutableStateFlow(YearMonth.now())
    
    val uiState: StateFlow<CalendarUiState> = combine(
        _selectedDate,
        _currentMonth,
        _selectedDate.flatMapLatest { date ->
            getEventsByDateUseCase(date)
        },
        _currentMonth.flatMapLatest { month ->
            val startDate = month.atDay(1)
            val endDate = month.atEndOfMonth()
            getDatesWithEventsUseCase(startDate, endDate)
        }
    ) { selectedDate, currentMonth, events, datesWithEvents ->
        CalendarUiState(
            selectedDate = selectedDate,
            currentMonth = currentMonth,
            eventsForSelectedDate = events,
            datesWithEvents = datesWithEvents.toSet()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CalendarUiState()
    )
    
    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
    }
    
    fun onMonthChanged(yearMonth: YearMonth) {
        _currentMonth.value = yearMonth
    }
    
    fun onNextMonth() {
        _currentMonth.value = _currentMonth.value.plusMonths(1)
    }
    
    fun onPreviousMonth() {
        _currentMonth.value = _currentMonth.value.minusMonths(1)
    }
    
    fun createEvent(event: Event) {
        viewModelScope.launch {
            try {
                createEventUseCase(event)
                // Events will be automatically updated through Flow
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }
    
    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            try {
                deleteEventUseCase(event)
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }
}
