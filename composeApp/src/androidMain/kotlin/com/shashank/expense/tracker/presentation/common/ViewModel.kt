package com.shashank.expense.tracker.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

actual abstract class CommonViewModel : ViewModel() {
    actual val viewModelScope: CoroutineScope
        get() = (this as ViewModel).viewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }

    actual fun <T> Flow<T>.stateInViewModel(initialValue: T): StateFlow<T> {
        return stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = initialValue
        )
    }
} 