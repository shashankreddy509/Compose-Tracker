package com.shashank.expense.tracker.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.shashank.expense.tracker.db.ExpenseTrackerDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    single { androidContext() }
    single<SqlDriver> {
        AndroidSqliteDriver(
            ExpenseTrackerDatabase.Schema,
            get(), // context
            "expense_tracker.db"
        )
    }
    single<ExpenseTrackerDatabase> { ExpenseTrackerDatabase(get()) }
}