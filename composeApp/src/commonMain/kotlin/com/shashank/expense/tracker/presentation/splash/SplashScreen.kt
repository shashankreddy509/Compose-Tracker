package com.shashank.expense.tracker.presentation.splash

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.shashank.expense.tracker.core.navigation.ScreenRoute

class SplashScreen : Screen, ScreenProvider {
    override val key: String = ScreenRoute.Splash.toString()
    
    @Composable
    override fun Content() {
        // TODO: Implement splash screen UI
    }
} 