package com.shashank.expense.tracker.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.shashank.expense.tracker.db.ExpenseTrackerDatabase
import com.shashank.expense.tracker.domain.model.Category
import com.shashank.expense.tracker.domain.model.TransactionType
import com.shashank.expense.tracker.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val database: ExpenseTrackerDatabase,
    private val dispatcher: kotlinx.coroutines.CoroutineDispatcher = Dispatchers.IO
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> {
        return database.expenseTrackerQueries.selectAllCategories()
            .asFlow()
            .mapToList(dispatcher)
            .map { categories ->
                categories.map { it.toCategory() }
            }
    }

    override fun getCategoryById(id: Long): Flow<Category?> {
        return database.expenseTrackerQueries.selectCategoryById(id.toString())
            .asFlow()
            .mapToList(dispatcher)
            .map { categories ->
                categories.firstOrNull()?.toCategory()
            }
    }

    override suspend fun addCategory(category: Category) = withContext(dispatcher) {
        database.expenseTrackerQueries.insertCategory(
            id = category.id.toString(),
            name = category.name,
            icon = category.icon,
            color = category.color.toString()
        )
    }

    override suspend fun updateCategory(category: Category) = withContext(dispatcher) {
        database.expenseTrackerQueries.updateCategory(
            name = category.name,
            icon = category.icon,
            color = category.color.toString(),
            id = category.id.toString()
        )
    }

    override suspend fun deleteCategory(id: Long) = withContext(dispatcher) {
        database.expenseTrackerQueries.deleteCategory(id.toString())
    }

    private fun com.shashank.expense.tracker.db.Category.toCategory(): Category {
        return Category(
            id = id.toLong(),
            name = name,
            icon = icon,
            color = color.toLongOrNull() ?: 0L,
            type = TransactionType.EXPENSE // Default type since it's not stored in the database
        )
    }
} 