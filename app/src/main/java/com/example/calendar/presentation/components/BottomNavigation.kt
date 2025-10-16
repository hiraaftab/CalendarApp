package com.example.calendar.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.R

enum class BottomNavItem(val titleResId: Int, val icon: ImageVector) {
    CALENDAR(R.string.nav_calendar, Icons.Filled.CalendarMonth),
    CLOCK(R.string.nav_clock, Icons.Filled.Schedule),
    NOTIFICATION(R.string.nav_notification, Icons.Filled.Notifications),
    PROFILE(R.string.nav_profile, Icons.Filled.Person)
}

@Composable
fun CalendarBottomNavigation(
    selectedItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        // Custom background from Figma
        Image(
            painter = painterResource(id = R.drawable.ic_path),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(119.dp),
            contentScale = ContentScale.FillBounds
        )
        
        // Navigation items
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter),
            containerColor = Color.Transparent,
            tonalElevation = 0.dp
        ) {
        BottomNavItem.values().forEach { item ->
            NavigationBarItem(
                selected = selectedItem == item,
                onClick = { onItemSelected(item) },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//                        if (selectedItem == item) {
//                            Box(
//                                modifier = Modifier
//                                    .size(6.dp)
//                                    .clip(CircleShape)
//                                    .background(Color(0xFF6A5AE0))
//                            )
//                            Spacer(modifier = Modifier.height(2.dp))
//                        }
                        Icon(
                            imageVector = item.icon,
                            contentDescription = stringResource(id = item.titleResId),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = null,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF735BF2),
                    unselectedIconColor = Color(0xFF9E9E9E),
                    selectedTextColor = Color(0xFF735BF2),
                    unselectedTextColor = Color(0xFF9E9E9E),
                    indicatorColor = Color.Transparent
                )
            )
        }
        }
    }
}
