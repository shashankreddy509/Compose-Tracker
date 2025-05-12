package com.shashank.expense.tracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.shashank.expense.tracker.domain.model.Expense
import com.shashank.expense.tracker.domain.usecase.GetExpensesUseCase
import com.shashank.expense.tracker.domain.usecase.GetCategoriesUseCase
import com.shashank.expense.tracker.domain.usecase.GetBudgetsUseCase
import com.shashank.expense.tracker.presentation.screens.dashboard.home.models.ExpenseModel
import com.shashank.expense.tracker.presentation.screens.dashboard.home.models.SpendingPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class HomeUiState(
    val expenses: List<Expense> = emptyList(),
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _selectedTimeFrame = MutableStateFlow("Month")
    val selectedTimeFrame: StateFlow<String> = _selectedTimeFrame.asStateFlow()

    private val _filteredExpenses = MutableStateFlow<List<ExpenseModel>>(emptyList())
    val filteredExpenses: StateFlow<List<ExpenseModel>> = _filteredExpenses.asStateFlow()

    private val _totalBalance = MutableStateFlow(0.0)
    val totalBalance: StateFlow<Double> = _totalBalance.asStateFlow()

    private val _totalIncome = MutableStateFlow(0.0)
    val totalIncome: StateFlow<Double> = _totalIncome.asStateFlow()

    private val _totalExpenses = MutableStateFlow(0.0)
    val totalExpenses: StateFlow<Double> = _totalExpenses.asStateFlow()

    private val _selectedMonth = MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).month)
    val selectedMonth: StateFlow<Month> = _selectedMonth.asStateFlow()

    private val _spendingData = MutableStateFlow<List<SpendingPoint>>(emptyList())
    val spendingData: StateFlow<List<SpendingPoint>> = _spendingData.asStateFlow()

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

    fun updateTimeFrame(timeFrame: String) {
        _selectedTimeFrame.value = timeFrame
    }

    fun selectMonth(month: Month) {
        _selectedMonth.value = month
    }
} 