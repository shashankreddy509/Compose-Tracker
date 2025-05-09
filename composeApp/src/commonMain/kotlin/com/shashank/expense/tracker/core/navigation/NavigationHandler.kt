package com.shashank.expense.tracker.core.navigation

import cafe.adriel.voyager.navigator.Navigator
import com.shashank.expense.tracker.presentation.screens.auth.LoginScreen
import com.shashank.expense.tracker.presentation.screens.auth.RegisterScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.DashboardScreen
import com.shashank.expense.tracker.presentation.screens.onboarding.OnboardingScreen
import com.shashank.expense.tracker.presentation.screens.splash.SplashScreen

/**
 * Centralized navigation handler for the app.
 * This function handles navigation from any screen to any other screen using Voyager's Navigator.
 */
fun navigateToScreen(navigator: Navigator?, route: ScreenRoute) {
    when (route) {
        ScreenRoute.Onboarding -> navigator?.push(OnboardingScreen())
        ScreenRoute.Login -> navigator?.push(LoginScreen())
        ScreenRoute.Register -> navigator?.push(RegisterScreen())
        ScreenRoute.Dashboard -> navigator?.push(DashboardScreen())
        ScreenRoute.Splash -> navigator?.push(SplashScreen())
        // Add other navigation cases as needed
    }
} 