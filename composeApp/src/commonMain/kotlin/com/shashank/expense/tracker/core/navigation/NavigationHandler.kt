package com.shashank.expense.tracker.core.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import cafe.adriel.voyager.navigator.Navigator
import com.shashank.expense.tracker.presentation.screens.auth.LoginScreen
import com.shashank.expense.tracker.presentation.screens.auth.RegisterScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.DashboardScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.transactions.TransactionEntryScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.transactions.components.TransactionType
import com.shashank.expense.tracker.presentation.screens.onboarding.OnboardingScreen
import com.shashank.expense.tracker.presentation.screens.splash.SplashScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * Centralized navigation handler for the app.
 * This function handles navigation from any screen to any other screen using Voyager's Navigator.
 */

val transactionType: String = TransactionType.EXPENSE.title

@ExperimentalMaterial3Api
@ExperimentalResourceApi
fun navigateToScreen(navigator: Navigator?, route: ScreenRoute,vararg args: String) {
    when (route) {
        ScreenRoute.Onboarding -> navigator?.push(OnboardingScreen())
        ScreenRoute.Login -> navigator?.push(LoginScreen())
        ScreenRoute.Register -> navigator?.push(RegisterScreen())
        ScreenRoute.Dashboard -> navigator?.push(DashboardScreen())
        ScreenRoute.Splash -> navigator?.push(SplashScreen())
        ScreenRoute.TransactionEntry -> {
            args.firstOrNull()?.let{
                when (it) {
                    TransactionType.INCOME.title -> navigator?.push(TransactionEntryScreen(TransactionType.INCOME))
                    TransactionType.EXPENSE.title -> navigator?.push(TransactionEntryScreen(TransactionType.EXPENSE))
                    else -> navigator?.push(TransactionEntryScreen(TransactionType.TRANSFER))
                }
            } ?: navigator?.push(TransactionEntryScreen(TransactionType.EXPENSE))
        }
        // Add other navigation cases as needed
    }
} 