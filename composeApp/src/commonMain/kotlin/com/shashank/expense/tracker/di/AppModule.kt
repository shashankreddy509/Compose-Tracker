package com.shashank.expense.tracker.di

import com.shashank.expense.tracker.data.repository.AuthRepositoryImpl
import com.shashank.expense.tracker.data.repository.BudgetRepositoryImpl
import com.shashank.expense.tracker.data.repository.CategoryRepositoryImpl
import com.shashank.expense.tracker.data.repository.ExpenseRepositoryImpl
import com.shashank.expense.tracker.db.AppDatabase
import com.shashank.expense.tracker.db.ExpenseTrackerDatabase
import com.shashank.expense.tracker.domain.repository.AuthRepository
import com.shashank.expense.tracker.domain.repository.BudgetRepository
import com.shashank.expense.tracker.domain.repository.CategoryRepository
import com.shashank.expense.tracker.domain.repository.ExpenseRepository
import com.shashank.expense.tracker.domain.usecase.*
import com.shashank.expense.tracker.presentation.viewmodel.AuthViewModel
import com.shashank.expense.tracker.presentation.viewmodel.*
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

expect fun platformModule(): org.koin.core.module.Module

val appModule = module {
    includes(platformModule())

    single { Firebase.auth }

    // Core dependencies
    single { AppDatabase(get())}
    single<ExpenseTrackerDatabase> { AppDatabase(get()).database }
    single<CoroutineDispatcher> { Dispatchers.IO }
    // Database driver/factory (platform-specific)
//    single { DatabaseDriverFactory(get()) }
//    single<ExpenseTrackerDatabase> { ExpenseTrackerDatabase(get<DatabaseDriverFactory>().createDriver()) }

//    single<ExpenseTrackerDatabase> { get<ExpenseTrackerDatabase>() }

    // Repositories
    single<ExpenseRepository> { ExpenseRepositoryImpl(get(), get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<BudgetRepository> { BudgetRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    
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
    viewModelOf(::HomeViewModel)
    viewModelOf(::AddExpenseViewModel)
    viewModelOf(::CategoryViewModel)
    viewModelOf(::ReportViewModel)
    viewModelOf(::BudgetViewModel)
    viewModelOf(::AuthViewModel)
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(appModule)
    }
}
