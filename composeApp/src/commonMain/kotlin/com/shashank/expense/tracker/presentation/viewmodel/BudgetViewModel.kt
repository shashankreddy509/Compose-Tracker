package com.shashank.expense.tracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.shashank.expense.tracker.data.repository.BudgetRepository
import com.shashank.expense.tracker.domain.model.Budget
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BudgetViewModel(
    private val budgetRepository: BudgetRepository
) : ViewModel() {
    private val _budgets = MutableStateFlow<List<Budget>>(emptyList())
    val budgets: StateFlow<List<Budget>> = _budgets.asStateFlow()

    private val _selectedBudget = MutableStateFlow<Budget?>(null)
    val selectedBudget: StateFlow<Budget?> = _selectedBudget.asStateFlow()

    init {
        loadBudgets()
    }

    private fun loadBudgets() {
        // TODO: Implement loading budgets from repository
    }

    fun addBudget(budget: Budget) {
        // TODO: Implement adding budget
    }

    fun updateBudget(budget: Budget) {
        // TODO: Implement updating budget
    }

    fun deleteBudget(budgetId: Long) {
        // TODO: Implement deleting budget
    }

    fun selectBudget(budget: Budget?) {
        _selectedBudget.update { budget }
    }
} 