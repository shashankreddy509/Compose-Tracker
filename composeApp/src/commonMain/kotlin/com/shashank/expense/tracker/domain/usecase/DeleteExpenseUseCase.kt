package com.shashank.expense.tracker.domain.usecase

import com.shashank.expense.tracker.data.repository.ExpenseRepository

class DeleteExpenseUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteExpense(id)
    }
} 