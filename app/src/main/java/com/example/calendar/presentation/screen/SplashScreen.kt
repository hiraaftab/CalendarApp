package com.example.calendar.presentation.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ), label = "alpha"
    )

    val slideOffset by animateFloatAsState(
        targetValue = if (startAnimation) 0f else 50f,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ), label = "slide"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2500)
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            
            // App Logo Icon - Left aligned
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .alpha(alpha)
                    .offset(y = slideOffset.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFF735BF2), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    // Calendar icon
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calender),
                        contentDescription = "Calendar icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(id = R.string.app_name_display),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E1E1E)
                )
            }

            Spacer(modifier = Modifier.height(80.dp))

            // Welcome text - Left aligned
            Text(
                text = "Welcome Michael!",
                fontSize = 18.sp,
                color = Color(0xFF9E9E9E),
                modifier = Modifier
                    .alpha(alpha)
                    .offset(y = slideOffset.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Main heading - Left aligned
            Text(
                text = "It'S Time to\nOrganize your Day!",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E1E),
                lineHeight = 40.sp,
                modifier = Modifier
                    .alpha(alpha)
                    .offset(y = slideOffset.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))
            
            // Girl image - takes full width and height
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .alpha(alpha)
                    .offset(y = slideOffset.dp)
            ) {
                // Girl image - covers full area
                Image(
                    painter = painterResource(id = R.drawable.ic_splash),
                    contentDescription = "Organize your day illustration",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds, // Covers entire area
                    alignment = Alignment.TopCenter
                )

                // Slide/Phone icon overlaid at bottom center
                Image(
                    painter = painterResource(id = R.drawable.ic_slide),
                    contentDescription = "Slide to continue",
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 40.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        // Colored background decorations
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 100.dp, y = (-50).dp)
                .size(200.dp)
                .background(
                    Color(0xFFE8E3FF),
                    shape = CircleShape
                )
                .alpha(0.5f)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-80).dp, y = 80.dp)
                .size(160.dp)
                .background(
                    Color(0xFFF5F3FF),
                    shape = CircleShape
                )
                .alpha(0.6f)
        )
    }
}