package com.shashank.expense.tracker.domain.repository

import com.shashank.expense.tracker.domain.model.Budget
import com.shashank.expense.tracker.domain.model.BudgetStatus
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    fun getAllBudgets(): Flow<List<Budget>>
    fun getBudgetById(id: Long): Flow<Budget?>
    fun getBudgetsByCategory(categoryId: Long): Flow<List<Budget>>
    suspend fun getBudgetStatus(budgetId: Long): BudgetStatus
    suspend fun addBudget(budget: Budget)
    suspend fun updateBudget(budget: Budget)
    suspend fun deleteBudget(id: Long)
}

data class BudgetStatus(
    val budget: Budget,
    val spent: Double,
    val remaining: Double,
    val percentageUsed: Double
)