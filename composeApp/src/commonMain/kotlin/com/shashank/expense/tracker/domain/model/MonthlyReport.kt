package com.shashank.expense.tracker.domain.model

data class MonthlyReport(
    val month: Int,
    val year: Int,
    val totalIncome: Double,
    val totalExpense: Double,
    val categoryDetails: List<CategoryReport>
)

data class CategoryReport(
    val categoryId: Long,
    val categoryName: String,
    val categoryIcon: String,
    val categoryColor: String,
    val amount: Double
) 