package com.shashank.expense.tracker.presentation.screens.auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.shashank.expense.tracker.core.navigation.ScreenRoute

class RegisterScreen : Screen, ScreenProvider {
    override val key: String = ScreenRoute.Register.toString()
    
    @Composable
    override fun Content() {
        // TODO: Implement register screen UI
    }
} 