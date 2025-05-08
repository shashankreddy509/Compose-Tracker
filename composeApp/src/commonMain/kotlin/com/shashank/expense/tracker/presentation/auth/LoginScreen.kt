package com.shashank.expense.tracker.presentation.auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.shashank.expense.tracker.core.navigation.ScreenRoute

class LoginScreen : Screen, ScreenProvider {
    override val key: String = ScreenRoute.Login.toString()
    
    @Composable
    override fun Content() {
        // TODO: Implement login screen UI
    }
} 