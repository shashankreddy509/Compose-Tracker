package com.example.expensetracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.expensetracker.ui.theme.AppTheme

@Composable
fun App() {
    val context = LocalContext.current
    val dataStorePreferences = remember { DataStorePreferences(context) }
    
    AppTheme {
        // Your app content here
    }
} 