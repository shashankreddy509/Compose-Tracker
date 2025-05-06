package com.shashank.expense.tracker.domain.usecase

import com.shashank.expense.tracker.domain.model.Expense
import com.shashank.expense.tracker.data.repository.ExpenseRepository

class UpdateExpenseUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense) {
        repository.updateExpense(expense)
    }
} 