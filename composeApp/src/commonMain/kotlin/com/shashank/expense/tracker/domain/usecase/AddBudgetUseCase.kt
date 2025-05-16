package com.shashank.expense.tracker.domain.usecase

import com.shashank.expense.tracker.domain.model.Budget
import com.shashank.expense.tracker.domain.repository.BudgetRepository

class AddBudgetUseCase(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budget: Budget) {
        repository.addBudget(budget)
    }
} 