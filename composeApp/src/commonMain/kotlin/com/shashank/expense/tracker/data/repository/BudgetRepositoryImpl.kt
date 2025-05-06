package com.shashank.expense.tracker.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.shashank.expense.tracker.db.ExpenseTrackerDatabase
import com.shashank.expense.tracker.domain.model.Budget
import com.shashank.expense.tracker.domain.model.BudgetStatus
import com.shashank.expense.tracker.domain.repository.BudgetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant

class BudgetRepositoryImpl(
    private val database: ExpenseTrackerDatabase,
    private val dispatcher: kotlinx.coroutines.CoroutineDispatcher = Dispatchers.IO
) : BudgetRepository {
    
    override fun getAllBudgets(): Flow<List<Budget>> {
        return database.expenseTrackerQueries.budgetSelectAll()
            .asFlow()
            .mapToList(dispatcher)
            .map { budgets ->
                budgets.map { it.toBudget() }
            }
    }

    override fun getBudgetsByCategory(categoryId: Long): Flow<List<Budget>> {
        return database.expenseTrackerQueries.budgetSelectByCategory(categoryId)
            .asFlow()
            .mapToList(dispatcher)
            .map { budgets ->
                budgets.map { it.toBudget() }
            }
    }

    override fun getBudgetById(id: Long): Flow<Budget?> {
        return database.expenseTrackerQueries.budgetSelectById(id)
            .asFlow()
            .mapToList(dispatcher)
            .map { budgets ->
                budgets.firstOrNull()?.toBudget()
            }
    }

    override suspend fun getBudgetStatus(budgetId: Long): BudgetStatus = withContext(dispatcher) {
        val budget = database.expenseTrackerQueries.budgetSelectById(budgetId).executeAsOneOrNull()
        if (budget == null) return@withContext BudgetStatus.NOT_FOUND

        val now = Instant.fromEpochMilliseconds(System.currentTimeMillis())
        val startDate = Instant.fromEpochMilliseconds(budget.startDate)
        val endDate = Instant.fromEpochMilliseconds(budget.endDate)

        when {
            now < startDate -> BudgetStatus.NOT_STARTED
            now > endDate -> BudgetStatus.EXPIRED
            else -> BudgetStatus.ACTIVE
        }
    }

    override suspend fun addBudget(budget: Budget) = withContext(dispatcher) {
        database.expenseTrackerQueries.budgetInsert(
            categoryId = budget.categoryId,
            amount = budget.amount,
            startDate = budget.startDate,
            endDate = budget.endDate
        )
    }

    override suspend fun updateBudget(budget: Budget) = withContext(dispatcher) {
        database.expenseTrackerQueries.budgetUpdate(
            categoryId = budget.categoryId,
            amount = budget.amount,
            startDate = budget.startDate,
            endDate = budget.endDate,
            id = budget.id
        )
    }

    override suspend fun deleteBudget(id: Long) = withContext(dispatcher) {
        database.expenseTrackerQueries.budgetDelete(id)
    }

    private fun com.shashank.expense.tracker.db.Budget.toBudget(): Budget {
        return Budget(
            id = id,
            categoryId = categoryId,
            amount = amount,
            startDate = startDate,
            endDate = endDate
        )
    }
} 