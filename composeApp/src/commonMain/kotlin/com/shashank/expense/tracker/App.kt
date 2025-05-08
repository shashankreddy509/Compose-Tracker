package com.shashank.expense.tracker

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.shashank.expense.tracker.presentation.screens.onboarding.OnboardingScreen
import com.shashank.expense.tracker.ui.theme.ExpenseTrackerTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ExpenseTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Navigator(OnboardingScreen())
        }
    }
}