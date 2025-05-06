package com.shashank.expense.tracker.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.shashank.expense.tracker.db.ExpenseTrackerDatabase
import com.shashank.expense.tracker.domain.model.CategoryReport
import com.shashank.expense.tracker.domain.model.Expense
import com.shashank.expense.tracker.domain.model.ExpenseType
import com.shashank.expense.tracker.domain.model.MonthlyReport
import com.shashank.expense.tracker.domain.model.PaymentMethod
import com.shashank.expense.tracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toInstant

class ExpenseRepositoryImpl(
    private val database: ExpenseTrackerDatabase,
    private val dispatcher: kotlinx.coroutines.CoroutineDispatcher = Dispatchers.IO
) : ExpenseRepository {

    override fun getAllExpenses(): Flow<List<Expense>> {
        return database.expenseTrackerQueries.expenseSelectAll()
            .asFlow()
            .mapToList(dispatcher)
            .map { expenses ->
                expenses.map { it.toExpense() }
            }
    }

    override fun getExpensesByCategory(categoryId: Long): Flow<List<Expense>> {
        return database.expenseTrackerQueries.expenseSelectByCategory(categoryId)
            .asFlow()
            .mapToList(dispatcher)
            .map { expenses ->
                expenses.map { it.toExpense() }
            }
    }

    override fun getExpensesByDateRange(startDate: Long, endDate: Long): Flow<List<Expense>> {
        return database.expenseTrackerQueries.expenseSelectByDateRange(startDate, endDate)
            .asFlow()
            .mapToList(dispatcher)
            .map { expenses ->
                expenses.map { it.toExpense() }
            }
    }

    override suspend fun addExpense(expense: Expense) = withContext(dispatcher) {
        database.expenseTrackerQueries.expenseInsert(
            amount = expense.amount,
            description = expense.description,
            categoryId = expense.categoryId,
            date = expense.date.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
            type = expense.type.name,
            paymentMethod = expense.paymentMethod.name,
            location = expense.location,
            receiptImageUrl = expense.receiptImageUrl
        )
    }

    override suspend fun updateExpense(expense: Expense) = withContext(dispatcher) {
        database.expenseTrackerQueries.expenseUpdate(
            amount = expense.amount,
            description = expense.description,
            categoryId = expense.categoryId,
            date = expense.date.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
            type = expense.type.name,
            paymentMethod = expense.paymentMethod.name,
            location = expense.location,
            receiptImageUrl = expense.receiptImageUrl,
            id = expense.id
        )
    }

    override suspend fun deleteExpense(id: Long) = withContext(dispatcher) {
        database.expenseTrackerQueries.expenseDelete(id)
    }

    override suspend fun getMonthlyReport(year: Int, month: Int): List<MonthlyReport> = withContext(dispatcher) {
        val monthStr = month.toString().padStart(2, '0')
        val yearMonth = "$year-$monthStr"
        
        database.expenseTrackerQueries.getMonthlyReport(yearMonth)
            .executeAsList()
            .map { report ->
                MonthlyReport(
                    month = report.month.toInt(),
                    year = report.year.toInt(),
                    totalIncome = report.income_total ?: 0.0,
                    totalExpense = report.expense_total ?: 0.0,
                    categoryDetails = listOf(
                        CategoryReport(
                            categoryId = report.categoryId,
                            categoryName = report.category_name,
                            categoryIcon = report.category_icon,
                            categoryColor = report.category_color,
                            amount = report.category_amount ?: 0.0
                        )
                    )
                )
            }
    }

    private fun com.shashank.expense.tracker.db.Expense.toExpense(): Expense {
        return Expense(
            id = id,
            amount = amount,
            description = description,
            categoryId = categoryId,
            date = Instant.fromEpochMilliseconds(date).toLocalDateTime(TimeZone.currentSystemDefault()),
            type = ExpenseType.valueOf(type),
            paymentMethod = PaymentMethod.valueOf(paymentMethod ?: PaymentMethod.OTHER.name),
            location = location,
            receiptImageUrl = receiptImageUrl
        )
    }
} 