package com.example.calendar.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calendar.presentation.screen.MainScreen
import com.example.calendar.presentation.screen.SplashScreen
import com.example.calendar.presentation.viewmodel.CalendarViewModel
import java.util.*

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Main : Screen("main")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: CalendarViewModel,
    locale: Locale,
    onLocaleChange: (Locale) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashComplete = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Main.route) {
            MainScreen(
                viewModel = viewModel,
                locale = locale,
                onLocaleChange = onLocaleChange
            )
        }
    }
}




