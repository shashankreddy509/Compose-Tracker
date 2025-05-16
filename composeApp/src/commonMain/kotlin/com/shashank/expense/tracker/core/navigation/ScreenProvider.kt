package com.shashank.expense.tracker.core.navigation

sealed interface ScreenRoute {
    data object Splash : ScreenRoute
    data object Onboarding : ScreenRoute
    data object Login : ScreenRoute
    data object Register : ScreenRoute
    data object Dashboard : ScreenRoute
    data object TransactionEntry : ScreenRoute
}