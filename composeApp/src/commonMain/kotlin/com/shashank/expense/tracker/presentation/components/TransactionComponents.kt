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
import com.shashank.expense.tracker.domain.model.Transaction
import com.shashank.expense.tracker.domain.model.TransactionType
import com.shashank.expense.tracker.domain.model.PaymentMethod
import kotlinx.datetime.*
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter
import com.shashank.expense.tracker.util.DateTimeUtil
import com.shashank.expense.tracker.util.StringFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionCard(
    transaction: Transaction,
    onTransactionClick: (Transaction) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { onTransactionClick(transaction) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = transaction.description,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = StringFormatter.format("%.2f", transaction.amount),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = "Category: ${transaction.category}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Account: ${transaction.account}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Date: ${DateTimeUtil.formatDateTime(transaction.date)}",
                style = MaterialTheme.typography.bodySmall
            )
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
    dateTime: LocalDateTime,
    onDateTimeSelected: (LocalDateTime) -> Unit,
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

private fun formatAmount(amount: Double, type: TransactionType): String {
    val prefix = when (type) {
        TransactionType.INCOME -> "+"
        TransactionType.EXPENSE -> "-"
    }
    return "$prefix$${String.format("%.2f", amount)}"
}

private fun formatDate(dateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")
    return dateTime.toJavaLocalDateTime().format(formatter)
}

@Composable
fun TransactionList(
    transactions: List<Transaction>,
    onTransactionClick: (Transaction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        transactions.forEach { transaction ->
            TransactionCard(
                transaction = transaction,
                onTransactionClick = onTransactionClick
            )
        }
    }
} 