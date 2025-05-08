package com.shashank.expense.tracker.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shashank.expense.tracker.domain.model.Budget
import com.shashank.expense.tracker.util.DateTimeUtil
import com.shashank.expense.tracker.util.StringFormatter
import kotlinx.datetime.*
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun BudgetProgressBar(
    currentAmount: Double,
    totalAmount: Double,
    modifier: Modifier = Modifier
) {
    val progress = (currentAmount / totalAmount).coerceIn(0.0, 1.0)
    
    Column(modifier = modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            progress = progress.toFloat(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = StringFormatter.format("$%.2f", currentAmount),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = StringFormatter.format("$%.2f", totalAmount),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun BudgetCard(
    budget: Budget,
    onBudgetClick: (Budget) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { onBudgetClick(budget) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = StringFormatter.format("Budget: $%.2f", budget.amount),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = StringFormatter.format("Spent: $%.2f", budget.spent),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = StringFormatter.format("Remaining: $%.2f", budget.remaining),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Category: ${budget.category}",
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Period: ${DateTimeUtil.formatMonthYear(budget.startDate)} - ${DateTimeUtil.formatMonthYear(budget.endDate)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun BudgetList(
    budgets: List<Budget>,
    onBudgetClick: (Budget) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        budgets.forEach { budget ->
            BudgetCard(
                budget = budget,
                onBudgetClick = onBudgetClick
            )
        }
    }
}

@Composable
fun AmountTrend(
    currentAmount: Double,
    previousAmount: Double,
    modifier: Modifier = Modifier
) {
    val trend = currentAmount - previousAmount
    val trendPercentage = if (previousAmount != 0.0) {
        (trend / previousAmount) * 100
    } else 0.0
    
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = StringFormatter.format("%s%.1f%%", if (trend >= 0) "+" else "", trendPercentage),
            color = when {
                trend > 0 -> MaterialTheme.colorScheme.primary
                trend < 0 -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

private fun formatDateRange(startDate: Long, endDate: Long): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd")
    val start = Instant.fromEpochMilliseconds(startDate).toLocalDateTime(TimeZone.currentSystemDefault())
    val end = Instant.fromEpochMilliseconds(endDate).toLocalDateTime(TimeZone.currentSystemDefault())
    return "${start.toJavaLocalDateTime().format(formatter)} - ${end.toJavaLocalDateTime().format(formatter)}"
} 