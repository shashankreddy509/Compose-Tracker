package com.shashank.expense.tracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.shashank.expense.tracker.domain.model.Expense
import com.shashank.expense.tracker.domain.usecase.GetExpensesUseCase
import com.shashank.expense.tracker.domain.usecase.GetCategoriesUseCase
import com.shashank.expense.tracker.domain.usecase.GetBudgetsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

data class HomeUiState(
    val expenses: List<Expense> = emptyList(),
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getBudgetsUseCase: GetBudgetsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        _uiState.update { it.copy(isLoading = true) }
        // TODO: Implement expense loading logic
    }

    fun refresh() {
        loadExpenses()
    }
} 