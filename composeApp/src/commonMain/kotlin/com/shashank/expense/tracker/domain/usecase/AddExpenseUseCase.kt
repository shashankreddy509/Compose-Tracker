package com.shashank.expense.tracker.domain.usecase

import com.shashank.expense.tracker.domain.model.Expense
import com.shashank.expense.tracker.domain.repository.ExpenseRepository

class AddExpenseUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(expense: Expense) {
        repository.addExpense(expense)
    }
} 