package com.shashank.expense.tracker.presentation.screens.dashboard.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.expense.tracker.presentation.screens.dashboard.home.components.MonthSelector
import com.shashank.expense.tracker.presentation.screens.dashboard.home.components.TransactionItem
import com.shashank.expense.tracker.presentation.viewmodel.HomeViewModel
import expense_tracker_compose.composeapp.generated.resources.Res
import expense_tracker_compose.composeapp.generated.resources.ic_sort
import org.jetbrains.compose.resources.painterResource

@Composable
fun TransactionScreen(viewModel: HomeViewModel) {
    val expenses by viewModel.filteredExpenses.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Row: Period Dropdown and Filter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MonthSelector(viewModel.selectedMonth.value, onMonthSelected = {
                viewModel.selectMonth(viewModel.selectedMonth.value)
            })
            IconButton(onClick = { /* TODO: Filter action */ }) {
                Icon(painter = painterResource(Res.drawable.ic_sort), contentDescription = "Filter")
            }
        }

        Spacer(Modifier.height(16.dp))

        // Financial Report Card
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* TODO: Navigate to report */ }
        ) {
            Text(
                "See your financial report",
                color = Color(0xFF6B4EFF),
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(Modifier.height(24.dp))

        // Today Section
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(expenses.size) { index ->
                TransactionItem(expense = expenses[index])
            }
        }
    }
}