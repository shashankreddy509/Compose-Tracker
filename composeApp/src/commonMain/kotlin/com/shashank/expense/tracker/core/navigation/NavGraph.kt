package com.shashank.expense.tracker.core.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.Navigator
import com.shashank.expense.tracker.presentation.splash.SplashScreen
import com.shashank.expense.tracker.presentation.onboarding.OnboardingScreen
import com.shashank.expense.tracker.presentation.auth.LoginScreen
import com.shashank.expense.tracker.presentation.auth.RegisterScreen
import com.shashank.expense.tracker.presentation.dashboard.DashboardScreen

fun initializeNavigation() {
    ScreenRegistry {
        register<SplashScreen> { SplashScreen() }
        register<OnboardingScreen> { OnboardingScreen() }
        register<LoginScreen> { LoginScreen() }
        register<RegisterScreen> { RegisterScreen() }
        register<DashboardScreen> { DashboardScreen() }
    }
}

@Composable
fun NavGraph(
    startDestination: ScreenRoute = ScreenRoute.Splash
) {
    val screen = when (startDestination) {
        ScreenRoute.Splash -> SplashScreen()
        ScreenRoute.Onboarding -> OnboardingScreen()
        ScreenRoute.Login -> LoginScreen()
        ScreenRoute.Register -> RegisterScreen()
        ScreenRoute.Dashboard -> DashboardScreen()
    }
    Navigator(screen)
} 