package com.shashank.expense.tracker.presentation.screens.dashboard.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shashank.expense.tracker.presentation.screens.dashboard.home.components.MonthSelector
import com.shashank.expense.tracker.presentation.viewmodel.BudgetViewModel
import org.koin.compose.viewmodel.koinViewModel
import kotlinx.datetime.Month

@Composable
fun BudgetScreen(
    onPrevMonth: () -> Unit = {},
    onNextMonth: () -> Unit = {},
    onCreateBudget: () -> Unit = {}
) {
    val viewModel: BudgetViewModel = koinViewModel()
    val currentMonth = "May" // Replace with state if you want dynamic months
    val selectedMonth by viewModel.selectedMonth.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF8B4DFF))
                .padding(top = 32.dp, bottom = 16.dp), // status bar + spacing
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val prev = Month.entries[(selectedMonth.ordinal + 11) % 12]
                    viewModel.selectMonth(prev)
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Previous Month",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                MonthSelector(
                    selectedMonth = selectedMonth,
                    onMonthSelected = { viewModel.selectMonth(it) }
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    val next =  Month.entries[(selectedMonth.ordinal + 1) % 12]
                    viewModel.selectMonth(next)
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next Month",
                        tint = Color.White
                    )
                }
            }
        }

        // Empty state message
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "You don't have a budget.",
                    color = Color(0xFFBDBDBD),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Let's make one so you in control.",
                    color = Color(0xFFBDBDBD),
                    fontSize = 16.sp
                )
            }
        }

        // Create budget button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = onCreateBudget,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B4DFF))
            ) {
                Text(
                    text = "Create a budget",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}