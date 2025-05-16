package com.shashank.expense.tracker.domain.usecase

import com.shashank.expense.tracker.domain.model.Category
import com.shashank.expense.tracker.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(private val repository: CategoryRepository) {
    operator fun invoke(): Flow<List<Category>> = repository.getAllCategories()
    
    fun getById(id: Long): Flow<Category?> = repository.getCategoryById(id)
} 