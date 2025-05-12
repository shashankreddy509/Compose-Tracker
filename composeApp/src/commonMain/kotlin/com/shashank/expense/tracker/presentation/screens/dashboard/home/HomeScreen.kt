package com.shashank.expense.tracker.presentation.screens.dashboard.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.expense.tracker.presentation.screens.dashboard.home.components.CardWithIncomesAndExpenses
import com.shashank.expense.tracker.presentation.screens.dashboard.home.components.HomeHeaderRow
import com.shashank.expense.tracker.presentation.screens.dashboard.home.components.SpendFrequencyGraph
import com.shashank.expense.tracker.presentation.screens.dashboard.home.components.TransactionItem
import com.shashank.expense.tracker.presentation.viewmodel.HomeViewModel
import com.shashank.expense.tracker.util.StringFormatter.formatCurrency

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel) {
    val expenses by viewModel.filteredExpenses.collectAsState()
    val totalBalance by viewModel.totalBalance.collectAsState()
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpenses by viewModel.totalExpenses.collectAsState()
    val selectedMonth by viewModel.selectedMonth.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Row: Profile and Notification
        HomeHeaderRow(viewModel)
        AnimatedContent(
            targetState = selectedMonth,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
            },
            label = "content transition"
        ) { _ ->
            // Rest of the content
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Balance Section
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Account Balance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF91919F)
                    )
                    Text(
                        text = formatCurrency(totalBalance),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Income and Expense Cards
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CardWithIncomesAndExpenses(
                            modifier = Modifier.weight(1f),
                            text = "Income",
                            totalIncomeOrExpense = totalIncome
                        )
                        CardWithIncomesAndExpenses(
                            modifier = Modifier.weight(1f),
                            text = "Expenses",
                            totalIncomeOrExpense = totalExpenses
                        )
                    }
                }
                // Spend Frequency Section
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Spend Frequency",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    SpendFrequencyGraph(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        viewModel = viewModel,
                        onTimeFrameSelected = { timeFrame ->
                            viewModel.updateTimeFrame(timeFrame)
                        }
                    )
                }

                // Recent Transactions
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recent Transaction",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        TextButton(
                            onClick = { /* TODO: Show all transactions */ }
                        ) {
                            Text(
                                text = "See All",
                                color = Color(0xFF7F3DFF)
                            )
                        }
                    }
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(expenses.size) { index ->
                            TransactionItem(expense = expenses[index])
                        }
                    }
                }
            }
        }
    }
}
