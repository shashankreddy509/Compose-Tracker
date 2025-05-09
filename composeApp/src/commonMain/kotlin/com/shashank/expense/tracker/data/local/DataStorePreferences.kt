package com.shashank.expense.tracker.data.local

import kotlinx.coroutines.flow.Flow

expect class DataStorePreferences {
    suspend fun saveString(key: String, value: String)
    fun getString(key: String): Flow<String?>
    suspend fun clear()
}
