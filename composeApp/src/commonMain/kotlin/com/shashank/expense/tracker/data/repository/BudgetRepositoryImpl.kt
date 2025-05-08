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
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class BudgetRepositoryImpl(
    private val database: ExpenseTrackerDatabase,
    private val dispatcher: kotlinx.coroutines.CoroutineDispatcher = Dispatchers.IO
) : BudgetRepository {
    
    override fun getAllBudgets(): Flow<List<Budget>> {
        return database.expenseTrackerQueries.selectAllBudgets()
            .asFlow()
            .mapToList(dispatcher)
            .map { budgets ->
                budgets.map { it.toBudget() }
            }
    }

    override fun getBudgetById(id: Long): Flow<Budget?> {
        return database.expenseTrackerQueries.selectBudgetById(id.toString())
            .asFlow()
            .mapToList(dispatcher)
            .map { budgets ->
                budgets.firstOrNull()?.toBudget()
            }
    }

    override fun getBudgetsByCategory(categoryId: Long): Flow<List<Budget>> {
        return database.expenseTrackerQueries.selectBudgetsByCategory(categoryId.toString())
            .asFlow()
            .mapToList(dispatcher)
            .map { budgets ->
                budgets.map { it.toBudget() }
            }
    }

    override suspend fun getBudgetStatus(budgetId: Long): BudgetStatus = withContext(dispatcher) {
        val budget = database.expenseTrackerQueries.selectBudgetById(budgetId.toString())
            .executeAsList()
            .firstOrNull()
        if (budget == null) return@withContext BudgetStatus.NOT_FOUND

        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val startDate = budget.startDate.toLocalDateTime()
        val endDate = budget.endDate.toLocalDateTime()

        when {
            now < startDate -> BudgetStatus.NOT_STARTED
            now > endDate -> BudgetStatus.EXPIRED
            else -> BudgetStatus.ACTIVE
        }
    }

    override suspend fun addBudget(budget: Budget) = withContext(dispatcher) {
        database.expenseTrackerQueries.insertBudget(
            id = budget.id,
            amount = budget.amount,
            spent = budget.spent,
            remaining = budget.remaining,
            category = budget.category,
            startDate = budget.startDate.toEpochMilliseconds(),
            endDate = budget.endDate.toEpochMilliseconds()
        )
    }

    override suspend fun updateBudget(budget: Budget) = withContext(dispatcher) {
        database.expenseTrackerQueries.updateBudget(
            id = budget.id,
            amount = budget.amount,
            spent = budget.spent,
            remaining = budget.remaining,
            category = budget.category,
            startDate = budget.startDate.toEpochMilliseconds(),
            endDate = budget.endDate.toEpochMilliseconds()
        )
    }

    override suspend fun deleteBudget(id: Long) = withContext(dispatcher) {
        database.expenseTrackerQueries.deleteBudget(id.toString())
    }

    private fun com.shashank.expense.tracker.db.Budget.toBudget(): Budget {
        return Budget(
            id = id,
            amount = amount,
            spent = spent,
            remaining = remaining,
            category = category,
            startDate = startDate.toLocalDateTime(),
            endDate = endDate.toLocalDateTime()
        )
    }

    private fun LocalDateTime.toEpochMilliseconds(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }

    private fun Long.toLocalDateTime(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }
} 