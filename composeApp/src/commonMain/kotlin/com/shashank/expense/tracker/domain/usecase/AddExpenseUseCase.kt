package com.shashank.expense.tracker.domain.usecase

import com.shashank.expense.tracker.data.repository.ExpenseRepository
import com.shashank.expense.tracker.domain.model.Expense

class AddExpenseUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(expense: Expense) {
        repository.addExpense(expense)
    }
} 