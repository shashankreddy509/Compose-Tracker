package com.shashank.expense.tracker.domain.usecase

import com.shashank.expense.tracker.data.repository.ExpenseRepository
import com.shashank.expense.tracker.data.repository.MonthlyReport

class GetMonthlyReportUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(year: Int, month: Int): List<MonthlyReport> =
        repository.getMonthlyReport(year, month)
} 