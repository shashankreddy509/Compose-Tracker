package com.shashank.expense.tracker.presentation.onboarding

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.shashank.expense.tracker.core.navigation.ScreenRoute

class OnboardingScreen : Screen, ScreenProvider {
    override val key: String = ScreenRoute.Onboarding.toString()
    
    @Composable
    override fun Content() {
        // TODO: Implement onboarding screen UI
    }
} 