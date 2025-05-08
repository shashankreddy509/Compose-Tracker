package com.shashank.expense.tracker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.shashank.expense.tracker.ui.theme.ExpenseTrackerTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val context = LocalContext.current
    val dataStorePreferences = remember { DataStorePreferences(context) }
    
    ExpenseTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            var showContent by remember { mutableStateOf(false) }
            
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Hello, ${if (showContent) "World!" else "..."}")
            }
        }
    }
}