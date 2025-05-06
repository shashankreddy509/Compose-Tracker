package com.shashank.expense.tracker.domain.usecase

import com.shashank.expense.tracker.data.repository.BudgetRepository
import com.shashank.expense.tracker.domain.model.Budget

class AddBudgetUseCase(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budget: Budget) {
        repository.addBudget(budget)
    }
} 