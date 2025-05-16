package com.shashank.expense.tracker.domain.usecase

import com.shashank.expense.tracker.domain.model.MonthlyReport
import com.shashank.expense.tracker.domain.repository.ExpenseRepository

class GetMonthlyReportUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(year: Int, month: Int): List<MonthlyReport> =
        repository.getMonthlyReport(year, month)
} 