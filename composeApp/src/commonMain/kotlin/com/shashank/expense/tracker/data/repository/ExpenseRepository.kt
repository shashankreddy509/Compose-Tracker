package com.shashank.expense.tracker.data.repository

import com.shashank.expense.tracker.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAllExpenses(): Flow<List<Expense>>
    fun getExpensesByCategory(categoryId: Long): Flow<List<Expense>>
    fun getExpensesByDateRange(startDate: Long, endDate: Long): Flow<List<Expense>>
    suspend fun addExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteExpense(id: Long)
    suspend fun getMonthlyReport(year: Int, month: Int): List<MonthlyReport>
}

data class MonthlyReport(
    val month: Int,
    val year: Int,
    val totalIncome: Double,
    val totalExpense: Double,
    val categoryDetails: List<CategoryDetail>
)

data class CategoryDetail(
    val categoryId: Long,
    val categoryName: String,
    val amount: Double
) 