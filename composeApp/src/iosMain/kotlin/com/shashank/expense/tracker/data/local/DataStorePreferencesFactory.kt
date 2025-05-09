package com.shashank.expense.tracker.data.local

actual class DataStorePreferencesFactory {
    actual fun create(): DataStorePreferences = DataStorePreferences()
}