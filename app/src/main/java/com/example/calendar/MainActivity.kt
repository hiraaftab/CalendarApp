package com.example.calendar

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.calendar.di.AppModule
import com.example.calendar.presentation.navigation.NavGraph
import com.example.calendar.ui.theme.CalendarTheme
import java.util.*

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        installSplashScreen()
        enableEdgeToEdge()
        
        setContent {
            var currentLocale by remember { 
                mutableStateOf(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        resources.configuration.locales[0]
                    } else {
                        resources.configuration.locale
                    }
                )
            }
            
            val layoutDirection = if (currentLocale.language == "ar") {
                LayoutDirection.Rtl
            } else {
                LayoutDirection.Ltr
            }
            
            CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                CalendarTheme {
                    val context = LocalContext.current
                    val viewModel = remember { AppModule.provideCalendarViewModel(context) }
                    val navController = rememberNavController()
                    
                    NavGraph(
                        navController = navController,
                        viewModel = viewModel,
                        locale = currentLocale,
                        onLocaleChange = { newLocale ->
                            setAppLocale(newLocale)
                            currentLocale = newLocale
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
    
    private fun setAppLocale(locale: Locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSystemService(LocaleManager::class.java).applicationLocales = 
                LocaleList(locale)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(locale.toLanguageTag())
            )
        }
        recreate()
    }
}