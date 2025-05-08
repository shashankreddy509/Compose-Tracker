package com.shashank.expense.tracker.di

import android.content.Context
import com.shashank.expense.tracker.data.local.DataStorePreferencesFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    single { androidContext() }
    single { DataStorePreferencesFactory(get()) }
} 