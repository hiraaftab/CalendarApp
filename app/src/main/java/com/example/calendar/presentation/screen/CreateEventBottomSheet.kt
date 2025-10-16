package com.example.calendar.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.R
import com.example.calendar.domain.model.Event
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventBottomSheet(
    selectedDate: LocalDate,
    onDismiss: () -> Unit,
    onCreateEvent: (Event) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var startHour by remember { mutableStateOf(10) }
    var startMinute by remember { mutableStateOf(0) }
    var endHour by remember { mutableStateOf(11) }
    var endMinute by remember { mutableStateOf(0) }
    var selectedColor by remember { mutableStateOf(0xFF735BF2.toInt()) }
    var remindMe by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Brainstorm") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        dragHandle = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(5.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color(0xFFE0E0E0))
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Title
            Text(
                text = stringResource(R.string.add_new_event),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E1E)
            )
            Spacer(Modifier.height(24.dp))

            // Event Title Input
            Text(
                text = "Event name*",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E1E1E)
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Event name*", color = Color(0xFFB0B0B0), fontSize = 15.sp) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF735BF2),
                    unfocusedBorderColor = Color(0xFFE5E5E5),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            Spacer(Modifier.height(18.dp))

            // Description Input
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Type the note here...", color = Color(0xFFB0B0B0), fontSize = 15.sp) },
                minLines = 3,
                maxLines = 5,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF735BF2),
                    unfocusedBorderColor = Color(0xFFE5E5E5),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            Spacer(Modifier.height(18.dp))

            // Date Display with icon
            OutlinedTextField(
                value = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                label = { Text(stringResource(R.string.date_label)) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = "Calendar",
                        tint = Color(0xFF9E9E9E),
                        modifier = Modifier.size(20.dp)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE5E5E5),
                    unfocusedBorderColor = Color(0xFFE5E5E5),
                    disabledBorderColor = Color(0xFFE5E5E5),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            Spacer(Modifier.height(18.dp))

            // Time Selection
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                TimeField(
                    label = stringResource(R.string.start_time),
                    hour = startHour,
                    minute = startMinute,
                    onChange = { h, m -> 
                        startHour = h
                        startMinute = m 
                    }
                )
                TimeField(
                    label = stringResource(R.string.end_time),
                    hour = endHour,
                    minute = endMinute,
                    onChange = { h, m -> 
                        endHour = h
                        endMinute = m 
                    }
                )
            }
            Spacer(Modifier.height(20.dp))

            // Reminds me toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.reminds_me),
                    fontSize = 15.sp,
                    color = Color(0xFF1E1E1E)
                )
                Switch(
                    checked = remindMe,
                    onCheckedChange = { remindMe = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF735BF2),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color(0xFFE0E0E0),
                        checkedBorderColor = Color.Transparent,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }
            Spacer(Modifier.height(20.dp))

            // Select Category
            Text(
                text = stringResource(R.string.select_category),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E1E1E)
            )
            Spacer(Modifier.height(14.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryChip(
                    text = "Brainstorm",
                    isSelected = selectedCategory == "Brainstorm",
                    color = Color(0xFF735BF2),
                    onClick = { selectedCategory = "Brainstorm"; selectedColor = 0xFF735BF2.toInt() },
                    modifier = Modifier.weight(1f)
                )
                CategoryChip(
                    text = "Design",
                    isSelected = selectedCategory == "Design",
                    color = Color(0xFF4CAF50),
                    onClick = { selectedCategory = "Design"; selectedColor = 0xFF4CAF50.toInt() },
                    modifier = Modifier.weight(1f)
                )
                CategoryChip(
                    text = "Workout",
                    isSelected = selectedCategory == "Workout",
                    color = Color(0xFF2196F3),
                    onClick = { selectedCategory = "Workout"; selectedColor = 0xFF2196F3.toInt() },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(12.dp))
            
            // Add new category
            Text(
                text = stringResource(R.string.add_new),
                fontSize = 14.sp,
                color = Color(0xFF735BF2),
                modifier = Modifier.clickable { /* Add new category */ }
            )
            Spacer(Modifier.height(28.dp))

            // Create Button
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onCreateEvent(
                            Event(
                                title = title,
                                description = description,
                                startTime = LocalTime.of(startHour, startMinute),
                                endTime = LocalTime.of(endHour, endMinute),
                                date = selectedDate,
                                color = selectedColor
                            )
                        )
                        onDismiss()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (title.isNotBlank()) Color(0xFF735BF2) else Color(0xFFE0E0E0),
                    disabledContainerColor = Color(0xFFE0E0E0),
                    contentColor = if (title.isNotBlank()) Color.White else Color(0xFF9E9E9E),
                    disabledContentColor = Color(0xFF9E9E9E)
                ),
                enabled = title.isNotBlank(),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 2.dp
                )
            ) {
                Text(
                    text = stringResource(R.string.create_event),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 0.5.sp
                )
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CategoryChip(
    text: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) color.copy(alpha = 0.15f) else color.copy(alpha = 0.1f))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(12.dp),
            contentAlignment = Alignment.Center
        ) {
            // Outer filled circle - always show color
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            // Inner white dot - always show
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
        Spacer(Modifier.width(6.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color(0xFF1E1E1E),
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
private fun RowScope.TimeField(label: String, hour: Int, minute: Int, onChange: (Int, Int) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    
    Column(Modifier.weight(1f)) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog = true },
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}",
                        fontSize = 16.sp,
                        color = Color(0xFF1E1E1E)
                    )
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = "Time",
                        tint = Color(0xFF9E9E9E),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }

    if (showDialog) {
        var h by remember { mutableStateOf(hour) }
        var m by remember { mutableStateOf(minute) }
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { 
                Text(
                    stringResource(R.string.select_time),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ) 
            },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Hour picker
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { h = (h + 1) % 24 }) { 
                            Text("▲", fontSize = 20.sp, color = Color(0xFF735BF2)) 
                        }
                        Text(
                            h.toString().padStart(2, '0'),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E1E1E)
                        )
                        IconButton(onClick = { h = (h - 1 + 24) % 24 }) { 
                            Text("▼", fontSize = 20.sp, color = Color(0xFF735BF2)) 
                        }
                    }
                    Text(
                        ":",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color(0xFF1E1E1E)
                    )
                    // Minute picker
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { m = (m + 15) % 60 }) { 
                            Text("▲", fontSize = 20.sp, color = Color(0xFF735BF2)) 
                        }
                        Text(
                            m.toString().padStart(2, '0'),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E1E1E)
                        )
                        IconButton(onClick = { m = (m - 15 + 60) % 60 }) { 
                            Text("▼", fontSize = 20.sp, color = Color(0xFF735BF2)) 
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { onChange(h, m); showDialog = false }
                ) {
                    Text(
                        stringResource(R.string.ok),
                        color = Color(0xFF735BF2),
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(
                        stringResource(R.string.cancel),
                        color = Color(0xFF9E9E9E)
                    )
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}
