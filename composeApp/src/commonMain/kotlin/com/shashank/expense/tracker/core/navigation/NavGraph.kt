package com.shashank.expense.tracker.core.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import com.shashank.expense.tracker.presentation.screens.auth.LoginScreen
import com.shashank.expense.tracker.presentation.screens.auth.RegisterScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.DashboardScreen
import com.shashank.expense.tracker.presentation.screens.onboarding.OnboardingScreen
import com.shashank.expense.tracker.presentation.screens.splash.SplashScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi

@ExperimentalMaterial3Api
@ExperimentalResourceApi
fun initializeNavigation() {
    ScreenRegistry {
        register<SplashScreen> { SplashScreen() }
        register<OnboardingScreen> { OnboardingScreen() }
        register<LoginScreen> { LoginScreen() }
        register<RegisterScreen> { RegisterScreen() }
        register<DashboardScreen> { DashboardScreen() }
    }
}

@ExperimentalMaterial3Api
@ExperimentalResourceApi
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