package com.shashank.expense.tracker.di

import com.shashank.expense.tracker.data.local.DataStorePreferences
import com.shashank.expense.tracker.data.local.DataStorePreferencesFactory
import com.shashank.expense.tracker.data.repository.BudgetRepositoryImpl
import com.shashank.expense.tracker.data.repository.CategoryRepositoryImpl
import com.shashank.expense.tracker.data.repository.ExpenseRepositoryImpl
import com.shashank.expense.tracker.db.AppDatabase
import com.shashank.expense.tracker.domain.repository.BudgetRepository
import com.shashank.expense.tracker.domain.repository.CategoryRepository
import com.shashank.expense.tracker.domain.repository.ExpenseRepository
import com.shashank.expense.tracker.domain.usecase.*
import com.shashank.expense.tracker.presentation.viewmodel.*
import org.koin.dsl.module

expect fun platformModule(): org.koin.core.module.Module

val appModule = module {
    includes(platformModule())

    // Core dependencies
    single { AppDatabase(get()) }
    single { DataStorePreferencesFactory(get()) }
    single { get<DataStorePreferencesFactory>().create() }
    
    // Repositories
    single<ExpenseRepository> { ExpenseRepositoryImpl(get(), get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<BudgetRepository> { BudgetRepositoryImpl(get()) }
    
    // Use cases
    single { GetExpensesUseCase(get()) }
    single { GetCategoriesUseCase(get()) }
    single { GetBudgetsUseCase(get()) }
    single { AddExpenseUseCase(get()) }
    single { UpdateExpenseUseCase(get()) }
    single { DeleteExpenseUseCase(get()) }
    single { AddCategoryUseCase(get()) }
    single { AddBudgetUseCase(get()) }
    single { GetMonthlyReportUseCase(get()) }
    
    // ViewModels
    single { HomeViewModel(get(), get(), get()) }
    single { AddExpenseViewModel(get(), get()) }
    single { CategoryViewModel(get()) }
    single { BudgetViewModel(get()) }
    single { ReportViewModel(get()) }
} 