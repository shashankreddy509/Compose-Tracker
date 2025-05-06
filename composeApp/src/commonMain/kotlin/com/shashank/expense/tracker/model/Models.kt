package com.shashank.expense.tracker.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class Expense(
    val id: Long = 0,
    val amount: Double,
    val description: String,
    val category: Category,
    val date: LocalDateTime,
    val type: TransactionType
)

data class Category(
    val id: Long = 0,
    val name: String,
    val icon: String,
    val color: Long
)

data class Budget(
    val id: Long = 0,
    val category: Category,
    val amount: Double,
    val period: BudgetPeriod,
    val startDate: LocalDate
)

enum class TransactionType {
    INCOME,
    EXPENSE
}

enum class BudgetPeriod {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

data class MonthlyReport(
    val month: Int,
    val year: Int,
    val totalIncome: Double,
    val totalExpenses: Double,
    val categoryBreakdown: Map<Category, Double>
) 