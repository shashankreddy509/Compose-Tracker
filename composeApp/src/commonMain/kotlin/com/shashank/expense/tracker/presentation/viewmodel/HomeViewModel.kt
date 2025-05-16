package com.shashank.expense.tracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.expense.tracker.domain.model.Expense
import com.shashank.expense.tracker.domain.usecase.GetExpensesUseCase
import com.shashank.expense.tracker.presentation.screens.dashboard.home.models.ExpenseModel
import com.shashank.expense.tracker.presentation.screens.dashboard.home.models.SpendingPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

class HomeViewModel(
    private val getExpensesUseCase: GetExpensesUseCase
) : ViewModel() {
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
        viewModelScope.launch {
            getExpensesUseCase().collectLatest { expenses ->
                val totalIncome = expenses.filter { it.type.name == "INCOME" }.sumOf { it.amount }
                val totalExpense = expenses.filter { it.type.name == "EXPENSE" }.sumOf { it.amount }
                val balance = totalIncome - totalExpense
                _totalIncome.value = totalIncome
                _totalExpenses.value = totalExpense
                _totalBalance.value = balance
                // Prepare spending trend (group by day, sum expenses)
                val spendingPoints = expenses.filter { it.type.name == "EXPENSE" }
                    .groupBy { it.date.date }
                    .map { (date, txs) ->
                        SpendingPoint(
                            value = txs.sumOf { it.amount }.toFloat(),
                            label = date.toString(),
                            timestamp = txs.first().date
                        )
                    }
                    .sortedBy { it.timestamp }
                _spendingData.value = spendingPoints
                // Optionally, map to ExpenseModel for filteredExpenses
                _filteredExpenses.value = expenses.map {
                    ExpenseModel(
                        id = it.id,
                        title = it.description,
                        amount = it.amount,
                        category = it.categoryId.toString(),
                        type = it.type.name,
                        tax = 0.0,
                        date = 0L,
                        createdAt = 0L
                    )
                }
                _uiState.update { state ->
                    state.copy(
                        expenses = expenses,
                        totalIncome = totalIncome,
                        totalExpense = totalExpense,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
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