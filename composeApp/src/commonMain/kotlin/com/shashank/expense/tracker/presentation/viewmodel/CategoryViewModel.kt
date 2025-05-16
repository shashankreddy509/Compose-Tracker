package com.shashank.expense.tracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.shashank.expense.tracker.domain.model.Category
import com.shashank.expense.tracker.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        // TODO: Implement loading categories from repository
    }

    fun addCategory(category: Category) {
        // TODO: Implement adding category
    }

    fun updateCategory(category: Category) {
        // TODO: Implement updating category
    }

    fun deleteCategory(categoryId: Long) {
        // TODO: Implement deleting category
    }

    fun selectCategory(category: Category?) {
        _selectedCategory.update { category }
    }
} 