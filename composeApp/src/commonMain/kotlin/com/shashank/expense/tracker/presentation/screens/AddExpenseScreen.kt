package com.shashank.expense.tracker.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shashank.expense.tracker.domain.model.TransactionType
import com.shashank.expense.tracker.domain.model.PaymentMethod
import com.shashank.expense.tracker.presentation.viewmodel.AddExpenseViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddExpenseViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showCategoryDialog by remember { mutableStateOf(false) }
    var showAccountDialog by remember { mutableStateOf(false) }
    var showPaymentMethodDialog by remember { mutableStateOf(false) }
    var showTypeDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Expense") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Amount
            OutlinedTextField(
                value = state.amount,
                onValueChange = viewModel::onAmountChange,
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth()
            )

            // Description
            OutlinedTextField(
                value = state.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            // Category Selection
            OutlinedTextField(
                value = state.categoryId?.toString() ?: "Select Category",
                onValueChange = { },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                enabled = false
            )
            Button(
                onClick = { showCategoryDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Category")
            }

            // Account Selection
            OutlinedTextField(
                value = state.accountId?.toString() ?: "Select Account",
                onValueChange = { },
                label = { Text("Account") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                enabled = false
            )
            Button(
                onClick = { showAccountDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Account")
            }

            // Type Selection
            OutlinedTextField(
                value = state.type.name,
                onValueChange = { },
                label = { Text("Type") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                enabled = false
            )
            Button(
                onClick = { showTypeDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Type")
            }

            // Payment Method Selection
            OutlinedTextField(
                value = state.paymentMethod.name,
                onValueChange = { },
                label = { Text("Payment Method") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                enabled = false
            )
            Button(
                onClick = { showPaymentMethodDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Payment Method")
            }

            // Location
            OutlinedTextField(
                value = state.location,
                onValueChange = viewModel::onLocationChange,
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )

            // Save Button
            Button(
                onClick = viewModel::addExpense,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Save")
                }
            }

            // Error Message
            state.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Category Selection Dialog
        if (showCategoryDialog) {
            AlertDialog(
                onDismissRequest = { showCategoryDialog = false },
                title = { Text("Select Category") },
                text = {
                    // TODO: Implement category list
                    Text("Category list will be implemented")
                },
                confirmButton = {
                    TextButton(onClick = { showCategoryDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }

        // Account Selection Dialog
        if (showAccountDialog) {
            AlertDialog(
                onDismissRequest = { showAccountDialog = false },
                title = { Text("Select Account") },
                text = {
                    // TODO: Implement account list
                    Text("Account list will be implemented")
                },
                confirmButton = {
                    TextButton(onClick = { showAccountDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }

        // Type Selection Dialog
        if (showTypeDialog) {
            AlertDialog(
                onDismissRequest = { showTypeDialog = false },
                title = { Text("Select Type") },
                text = {
                    Column {
                        TransactionType.values().forEach { type ->
                            TextButton(
                                onClick = {
                                    viewModel.onTypeChange(type)
                                    showTypeDialog = false
                                }
                            ) {
                                Text(type.name)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showTypeDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }

        // Payment Method Selection Dialog
        if (showPaymentMethodDialog) {
            AlertDialog(
                onDismissRequest = { showPaymentMethodDialog = false },
                title = { Text("Select Payment Method") },
                text = {
                    Column {
                        PaymentMethod.values().forEach { method ->
                            TextButton(
                                onClick = {
                                    viewModel.onPaymentMethodChange(method)
                                    showPaymentMethodDialog = false
                                }
                            ) {
                                Text(method.name)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showPaymentMethodDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
} 