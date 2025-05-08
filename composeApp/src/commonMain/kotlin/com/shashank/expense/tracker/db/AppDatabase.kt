package com.shashank.expense.tracker.db

import app.cash.sqldelight.db.SqlDriver
import com.shashank.expense.tracker.db.ExpenseTrackerDatabase

class AppDatabase(private val driver: SqlDriver) {
    val database: ExpenseTrackerDatabase = ExpenseTrackerDatabase(driver)
} 