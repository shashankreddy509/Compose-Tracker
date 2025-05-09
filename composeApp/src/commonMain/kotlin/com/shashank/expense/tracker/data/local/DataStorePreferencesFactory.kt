package com.shashank.expense.tracker.data.local

expect class DataStorePreferencesFactory() {
    fun create(): DataStorePreferences
}