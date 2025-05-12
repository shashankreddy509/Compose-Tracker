package com.shashank.expense.tracker.presentation.screens.dashboard.home.models

data class ExpenseModel(
    val id: Long,
    val title: String,
    val amount: Double,
    val category: String,
    val type: String,
    val tax: Double,
    val date: Long,
    val createdAt: Long
) {
    val isIncome: Boolean
        get() = type == "income"
}