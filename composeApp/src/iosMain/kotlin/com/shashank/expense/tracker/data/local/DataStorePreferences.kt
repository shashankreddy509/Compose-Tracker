package com.shashank.expense.tracker.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import platform.Foundation.NSUserDefaults
import platform.Foundation.NSBundle

actual class DataStorePreferences {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    actual suspend fun saveString(key: String, value: String) {
        userDefaults.setObject(value, key)
        userDefaults.synchronize()
    }

    actual fun getString(key: String): Flow<String?> = flow {
        emit(userDefaults.stringForKey(key))
    }

    actual suspend fun clear() {
        val bundleIdentifier = NSBundle.mainBundle.bundleIdentifier
        if (bundleIdentifier != null) {
            userDefaults.removePersistentDomainForName(bundleIdentifier)
            userDefaults.synchronize()
        }
    }
}

