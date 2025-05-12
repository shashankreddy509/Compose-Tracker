package com.shashank.expense.tracker

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.shashank.expense.tracker.presentation.screens.dashboard.DashboardScreen
import com.shashank.expense.tracker.presentation.screens.onboarding.OnboardingScreen
import com.shashank.expense.tracker.presentation.viewmodel.AuthViewModel
import com.shashank.expense.tracker.ui.theme.ExpenseTrackerTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    ExpenseTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navigator = LocalNavigator.current
            val viewModel: AuthViewModel = koinViewModel()
            if(viewModel.userLoggedInEvent.value) {
                Navigator(DashboardScreen())
            } else {
                Navigator(OnboardingScreen())
            }
        }
    }
}