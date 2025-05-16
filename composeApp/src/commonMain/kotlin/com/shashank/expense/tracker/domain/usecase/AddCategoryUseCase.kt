package com.shashank.expense.tracker.domain.usecase

import com.shashank.expense.tracker.domain.model.Category
import com.shashank.expense.tracker.domain.repository.CategoryRepository

class AddCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        repository.addCategory(category)
    }
} 