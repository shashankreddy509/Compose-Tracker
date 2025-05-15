package com.shashank.expense.tracker.presentation.screens.dashboard.transactions.components

import androidx.compose.ui.graphics.Color

enum class TransactionType(val title: String, val color: Color) {
    EXPENSE("Expense", Color(0xFFE74C3C)),
    INCOME("Income", Color(0xFF3498DB)),
    TRANSFER("Income", Color(0xFF27AE60))
}