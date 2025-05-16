package com.shashank.expense.tracker.di

import app.cash.sqldelight.db.SqlDriver
import com.shashank.expense.tracker.db.ExpenseTrackerDatabase
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.koin.dsl.module

actual fun platformModule() = module {
    single<SqlDriver> {
        NativeSqliteDriver(
            ExpenseTrackerDatabase.Schema,
            "expense_tracker.db"
        )
    }
    single<ExpenseTrackerDatabase> { ExpenseTrackerDatabase(get()) }
}