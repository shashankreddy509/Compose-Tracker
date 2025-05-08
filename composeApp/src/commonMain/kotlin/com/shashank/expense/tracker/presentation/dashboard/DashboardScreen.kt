package com.shashank.expense.tracker.presentation.dashboard

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.shashank.expense.tracker.core.navigation.ScreenRoute

class DashboardScreen : Screen, ScreenProvider {
    override val key: String = ScreenRoute.Dashboard.toString()
    
    @Composable
    override fun Content() {
        // TODO: Implement dashboard screen UI
    }
} 