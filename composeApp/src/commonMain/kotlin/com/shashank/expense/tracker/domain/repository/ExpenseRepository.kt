package com.shashank.expense.tracker.domain.repository

import com.shashank.expense.tracker.domain.model.Expense
import com.shashank.expense.tracker.domain.model.MonthlyReport
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