package com.example.calendar.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calendar.presentation.components.BottomNavItem
import com.example.calendar.presentation.components.CalendarBottomNavigation
import com.example.calendar.presentation.viewmodel.CalendarViewModel
import java.util.*

@Composable
fun MainScreen(
    viewModel: CalendarViewModel,
    locale: Locale,
    onLocaleChange: (Locale) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedNavItem by remember { mutableStateOf(BottomNavItem.CALENDAR) }
    var showCreateEventSheet by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                Box {
                    CalendarBottomNavigation(
                        selectedItem = selectedNavItem,
                        onItemSelected = { selectedNavItem = it }
                    )
                }
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            when (selectedNavItem) {
                BottomNavItem.CALENDAR -> {
                    CalendarScreen(
                        uiState = uiState,
                        onDateSelected = viewModel::onDateSelected,
                        onPreviousMonth = viewModel::onPreviousMonth,
                        onNextMonth = viewModel::onNextMonth,
                        locale = locale,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
                BottomNavItem.CLOCK -> {
                    EmptyScreen(message = "Clock Screen")
                }
                BottomNavItem.NOTIFICATION -> {
                    EmptyScreen(message = "Notifications Screen")
                }
                BottomNavItem.PROFILE -> {
                    EmptyScreen(message = "Profile Screen")
                }
            }
        }
        
        // Centered FAB in bottom navigation notch
        FloatingActionButton(
            onClick = { showCreateEventSheet = true },
            containerColor = Color(0xFF735BF2),
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
                .size(56.dp),
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add event",
                modifier = Modifier.size(28.dp)
            )
        }
    }

    // Show Create Event Bottom Sheet
    if (showCreateEventSheet) {
        println("Showing Create Event Bottom Sheet")
        CreateEventBottomSheet(
            selectedDate = uiState.selectedDate,
            onDismiss = { 
                println("Dismissing Create Event Sheet")
                showCreateEventSheet = false 
            },
            onCreateEvent = { event ->
                println("Creating event: ${event.title}")
                viewModel.createEvent(event)
                showCreateEventSheet = false
            }
        )
    }
}

@Composable
fun EmptyScreen(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF9E9E9E)
        )
    }
}
