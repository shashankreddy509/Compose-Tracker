package com.shashank.expense.tracker.domain.usecase

import com.shashank.expense.tracker.data.repository.ExpenseRepository
import com.shashank.expense.tracker.domain.model.Expense
import kotlinx.coroutines.flow.Flow

class GetExpensesUseCase(private val repository: ExpenseRepository) {
    operator fun invoke(): Flow<List<Expense>> = repository.getAllExpenses()
    
    fun getByCategory(categoryId: Long): Flow<List<Expense>> = 
        repository.getExpensesByCategory(categoryId)
    
    fun getByDateRange(startDate: Long, endDate: Long): Flow<List<Expense>> =
        repository.getExpensesByDateRange(startDate, endDate)
} 