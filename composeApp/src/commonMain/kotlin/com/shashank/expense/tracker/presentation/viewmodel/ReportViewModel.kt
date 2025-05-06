package com.shashank.expense.tracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.expense.tracker.data.repository.MonthlyReport
import com.shashank.expense.tracker.domain.usecase.GetMonthlyReportUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

data class ReportState(
    val reports: List<MonthlyReport> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ReportViewModel(
    private val getMonthlyReportUseCase: GetMonthlyReportUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ReportState())
    val state: StateFlow<ReportState> = _state.asStateFlow()

    init {
        loadCurrentMonthReport()
    }

    private fun loadCurrentMonthReport() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)
                val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
                val reports = getMonthlyReportUseCase(today.year, today.monthNumber)
                _state.value = _state.value.copy(
                    reports = reports,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "An error occurred"
                )
            }
        }
    }

    fun loadReport(year: Int, month: Int) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)
                val reports = getMonthlyReportUseCase(year, month)
                _state.value = _state.value.copy(
                    reports = reports,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "An error occurred"
                )
            }
        }
    }
} 