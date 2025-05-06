package com.shashank.expense.tracker.domain.usecase

import com.shashank.expense.tracker.data.repository.BudgetRepository
import com.shashank.expense.tracker.domain.model.Budget
import kotlinx.coroutines.flow.Flow

class GetBudgetsUseCase(private val repository: BudgetRepository) {
    operator fun invoke(): Flow<List<Budget>> = repository.getAllBudgets()
    
    fun getById(id: Long): Flow<Budget?> = repository.getBudgetById(id)
    
    fun getByCategory(categoryId: Long): Flow<List<Budget>> = 
        repository.getBudgetsByCategory(categoryId)
    
    suspend fun getBudgetStatus(budgetId: Long) = repository.getBudgetStatus(budgetId)
} 