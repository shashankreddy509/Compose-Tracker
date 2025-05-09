package com.shashank.expense.tracker.data.local

import android.content.Context

actual class DataStorePreferencesFactory(private val context: Context) {
    actual fun create(): DataStorePreferences = DataStorePreferences(context)
}
