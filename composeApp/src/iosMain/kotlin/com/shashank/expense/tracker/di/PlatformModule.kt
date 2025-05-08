package com.shashank.expense.tracker.di

import com.shashank.expense.tracker.data.local.DataStorePreferencesFactory
import org.koin.dsl.module

actual fun platformModule() = module {
    single { DataStorePreferencesFactory() }
} 