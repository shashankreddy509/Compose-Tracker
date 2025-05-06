package com.shashank.expense.tracker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import expense_tracker_compose.composeapp.generated.resources.Res
import expense_tracker_compose.composeapp.generated.resources.compose_multiplatform
import com.shashank.expense.tracker.ui.theme.ExpenseTrackerTheme

@Composable
@Preview
fun App() {
    ExpenseTrackerTheme {
        Surface(color = MaterialTheme.colors.background) {
            var showContent by remember { mutableStateOf(false) }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
            }
        }
    }
}