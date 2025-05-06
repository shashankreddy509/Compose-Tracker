package com.shashank.expense.tracker.data.repository

import com.shashank.expense.tracker.domain.model.Budget
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    fun getAllBudgets(): Flow<List<Budget>>
    fun getBudgetById(id: Long): Flow<Budget?>
    fun getBudgetsByCategory(categoryId: Long): Flow<List<Budget>>
    suspend fun addBudget(budget: Budget)
    suspend fun updateBudget(budget: Budget)
    suspend fun deleteBudget(id: Long)
    suspend fun getBudgetStatus(budgetId: Long): BudgetStatus
}

data class BudgetStatus(
    val budget: Budget,
    val spent: Double,
    val remaining: Double,
    val percentageUsed: Double
) 