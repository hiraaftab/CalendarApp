package com.example.calendar.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.domain.model.Event
import java.time.format.DateTimeFormatter

@Composable
fun EventListItem(
    event: Event,
    modifier: Modifier = Modifier,
    visible: Boolean = true
) {
    var isExpanded by remember { mutableStateOf(false) }
    
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Color dot indicator
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .size(12.dp)
                        .clip(androidx.compose.foundation.shape.CircleShape)
                        .background(Color(event.color))
                )
                
                Spacer(modifier = Modifier.width(16.dp))

                // Event details
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Time range
                    Text(
                        text = "${event.startTime.format(DateTimeFormatter.ofPattern("HH:mm"))}-${event.endTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF9E9E9E),
                        fontSize = 14.sp,
                        letterSpacing = 0.2.sp
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Event title
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E1E),
                        fontSize = 18.sp
                    )
                    
                    if (event.description.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        
                        // Track if text overflows one line
                        var hasOverflow by remember { mutableStateOf(false) }
                        
                        // Description with expand/collapse
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = event.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFB0B0B0),
                                fontSize = 14.sp,
                                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false),
                                onTextLayout = { textLayoutResult ->
                                    // Check if text is truncated (doesn't fit in one line)
                                    hasOverflow = textLayoutResult.hasVisualOverflow || 
                                                 textLayoutResult.didOverflowHeight ||
                                                 textLayoutResult.lineCount > 1
                                }
                            )
                            
                            // Only show "View more/less" if text actually overflows
                            if (hasOverflow && !isExpanded) {
                                Text(
                                    text = " View more",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF735BF2),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.clickable { isExpanded = !isExpanded }
                                )
                            }
                        }
                        
                        if (isExpanded && hasOverflow) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "View less",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF735BF2),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.clickable { isExpanded = !isExpanded }
                            )
                        }
                    }
                }
                
                // Menu icon (three dots)
                Text(
                    text = "⋮",
                    fontSize = 24.sp,
                    color = Color(0xFFCCCCCC),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
