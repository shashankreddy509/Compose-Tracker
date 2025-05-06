package com.shashank.expense.tracker.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.shashank.expense.tracker.domain.model.Expense
import com.shashank.expense.tracker.domain.model.ExpenseType
import com.shashank.expense.tracker.domain.model.PaymentMethod
import kotlinx.datetime.*
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionCard(
    expense: Expense,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = expense.description,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = expense.paymentMethod?.name ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = formatAmount(expense.amount, expense.type),
                    style = MaterialTheme.typography.titleMedium,
                    color = when (expense.type) {
                        ExpenseType.INCOME -> MaterialTheme.colorScheme.primary
                        ExpenseType.EXPENSE -> MaterialTheme.colorScheme.error
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatDate(expense.date),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Only allow numbers and decimal point
            if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                onValueChange(newValue)
            }
        },
        modifier = modifier.fillMaxWidth(),
        label = label?.let { { Text(it) } },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
    )
}

@Composable
fun DateTimePicker(
    dateTime: kotlinx.datetime.LocalDateTime,
    onDateTimeSelected: (kotlinx.datetime.LocalDateTime) -> Unit,
    modifier: Modifier = Modifier
) {
    // Platform-specific implementation will be needed
    // This is a placeholder for the common interface
    Text(
        text = formatDate(dateTime),
        modifier = modifier
    )
}

@Composable
fun ReceiptImageViewer(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    // Platform-specific implementation will be needed
    // This is a placeholder for the common interface
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        // Image loading and zoom functionality will be implemented per platform
    }
}

private fun formatAmount(amount: Double, type: ExpenseType): String {
    val prefix = when (type) {
        ExpenseType.INCOME -> "+"
        ExpenseType.EXPENSE -> "-"
    }
    return "$prefix$${String.format("%.2f", amount)}"
}

private fun formatDate(dateTime: kotlinx.datetime.LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")
    return dateTime.toJavaLocalDateTime().format(formatter)
} 